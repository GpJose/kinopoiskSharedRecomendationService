package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dto.profile.UserProfileDTO;
import ru.kinoposisk.exception.friendsExceptions.FriendsAccessDenyException;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;
import ru.kinoposisk.repository.FriendsRepository;
import ru.kinoposisk.service.interfaces.FriendsService;
import ru.kinoposisk.service.interfaces.UsersService;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;
    private final UsersService usersService;

    public FriendsServiceImpl(FriendsRepository friendsRepository, UsersService usersService) {
        this.friendsRepository = friendsRepository;
        this.usersService = usersService;
    }

    @Override
    public void add(Users user, Users friend) {

        Friends friendRequest = getFriendRequest(user, friend);
        if (friendRequest.getFriendRequestStatus() == FriendsRequestStatusEnum.PENDING) {
            friendRequest.setFriendRequestStatus(FriendsRequestStatusEnum.ACCEPTED);

            user.getFriends().stream()
                    .filter(friends -> friends.getFriendId().getLogin().equals(friend.getLogin()))
                    .findFirst()
                    .ifPresent(f -> {
                        f.setFriendRequestStatus(FriendsRequestStatusEnum.ACCEPTED);
                        usersService.updateUser(user);
                    });

            friend.getFriends().add(Friends.builder()
                    .friendId(user)
                    .friendRequestStatus(FriendsRequestStatusEnum.ACCEPTED)
                    .userId(friend)
                    .build());
            usersService.updateUser(friend);

        } else {
            throw new UsernameNotFoundException("Friend request does not exist");
        }
    }

    @Override
    public void removeFriend(Users user, Users friend) {

        Friends friendRequest = getFriendRequest(user, friend);
        user.getFriends().remove(friendRequest);
        friend.getFriends().remove(friendRequest);
        usersService.updateUser(user);
        usersService.updateUser(friend);
    }

    @Override
    public List<Friends> getAllFriendsRequestByUser(Users user) {

        return friendsRepository.findByUserIdAndUserId_Friends_FriendRequestStatusOrderByUserId_Friends_DateDesc(user, FriendsRequestStatusEnum.PENDING);
    }



    @Override
    public Friends sendFriendRequest(Users user, Users friend) {

        Optional<Friends> friendRequestStatus = friendsRepository.findByUserIdAndFriendIdAndFriendRequestStatus(user, friend, FriendsRequestStatusEnum.PENDING);

        if (friendRequestStatus.isPresent()) {

            throw new DuplicateKeyException("Friend request already exist");
        }

        else {

            return friendsRepository.save(Friends.builder()
                    .friendRequestStatus(FriendsRequestStatusEnum.PENDING)
                    .friendId(friend)
                    .userId(user)
                    .build());
        }
    }

    @Override
    public FriendsRequestStatusEnum findFriendRequestStatus(Users user, Users friend) {

        return getFriendRequest(user, friend).getFriendRequestStatus();
    }


    private Friends getFriendRequest(Users user, Users friend) {
        Optional<Friends> friendRequestStatus = friendsRepository.findByUserIdAndFriendId(user, friend);

        if (!friendRequestStatus.isPresent()) {

            throw new UsernameNotFoundException("Friend request not exist");
        }

        return friendRequestStatus.get();
    }

    @Override
    public UserProfileDTO getFriendProfile(Users user, Users friend) throws FriendsAccessDenyException {

        if(getFriendRequest(user,friend).getFriendRequestStatus() == FriendsRequestStatusEnum.ACCEPTED) {

            return usersService.getProfile(friend);
        }
        else {
            throw new FriendsAccessDenyException(friend);
        }
    }


}
