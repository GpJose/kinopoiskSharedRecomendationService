package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dto.auth.AuthSignUpDTO;
import ru.kinoposisk.dto.changePass.ChangePassByEmailDTO;
import ru.kinoposisk.dto.changePass.ChangePassByLoginDTO;
import ru.kinoposisk.dto.profile.FriendProfileDTO;
import ru.kinoposisk.dto.profile.MovieHistoryDTO;
import ru.kinoposisk.dto.profile.UserProfileDTO;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.UsersRepository;
import ru.kinoposisk.service.interfaces.UsersService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(AuthSignUpDTO user) {

        Users userEntity = Users.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
        add(userEntity);
    }

    @Override
    public Users add(Users users) {

        if (isLoginAndEmailUnique(users.getLogin(), users.getEmail())) {

            log.info("User with login:${} and email:${} has benn created successfully", users.getLogin(), users.getEmail());
            return usersRepository.save(users);
        }
        else {
            throw new DuplicateKeyException("User with login: " + users.getLogin() + " or email: " + users.getEmail() + " already exist");
        }
    }

    @Override
    public void deleteByID(Long id) {

        if (usersRepository.existsById(id)) {

            usersRepository.deleteById(id);
            log.info("User with id:{} has been deleted", id);

        } else {
            throw new UsernameNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public Long deleteByLogin(String login) {

        if (isLoginUnique(login)) {
            log.info("User with id:${} has been deleted", login);

            return usersRepository.deleteByLogin(login);
        }
        else {
            throw new UsernameNotFoundException("User with login: "+login+ " not found");
        }
    }

    @Override
    public Users updateUser(Users user) {

        return usersRepository.save(user);
    }

    @Override
    public String getRole(Long id) {

        return findByID(id).getRoles().toString();
    }

    @Override
    public String getRole(String login) {

        return findByLogin(login).getRoles().toString();
    }

    @Override
    public Users findByID(Long id) {

        return usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Users findByLogin(String login) {

        return usersRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public Users findByEmail(String email) {

        return usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public List<Users> getAll() {

        return usersRepository.findAll();
    }

    @Override
    public void changePassword(ChangePassByLoginDTO changePassByLoginDTO) throws IOException {

        changePassword(
                findByLogin(changePassByLoginDTO.getLogin()),
                changePassByLoginDTO.getNewPassword(),
                changePassByLoginDTO.getOldPassword());
    }

    @Override
    public void changePassword(ChangePassByEmailDTO changePassByEmailDTO) throws IOException {

        changePassword(
                findByEmail(changePassByEmailDTO.getEmail()),
                changePassByEmailDTO.getNewPassword(),
                changePassByEmailDTO.getOldPassword());
    }

    private void changePassword(Users user, String newPassword, String oldPassword ) throws IOException {

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {

            user.setPassword(passwordEncoder.encode(newPassword));
            updateUser(user);
            log.info("Password has been changed for user with login:{} email:{}", user.getLogin(), user.getEmail());

        } else {
            throw new IOException("Old password is incorrect");
        }
    }

    @Override
    public boolean changeActiveStatus(String login, Boolean isActive) {

        Users user = findByLogin(login);
        user.setActive(isActive);

        return updateUser(user).isActive();
    }

    private boolean isEmailUnique(String email) {

        return !usersRepository.findByEmail(email).isPresent();
    }

    private boolean isLoginUnique(String login) {

        return !usersRepository.findByLogin(login).isPresent();
    }

    private boolean isLoginAndEmailUnique(String login, String email) {

        return isLoginUnique(login) && isEmailUnique(email);
    }

    @Override
    public UserProfileDTO getProfile(Users user) {

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setLogin(user.getLogin());
        userProfileDTO.setEmail(user.getEmail());

        List<FriendProfileDTO> friends = user.getFriends().stream().map(friendMap -> {
            FriendProfileDTO friendDTO = new FriendProfileDTO();
            friendDTO.setFriendLogin(friendMap.getFriendId().getLogin());

            return friendDTO;

        }).collect(Collectors.toList());
        userProfileDTO.setFriends(friends);

        List<MovieHistoryDTO> movieHistories = user.getMovieHistoriesList()
                .stream()
                .map(history -> {
                    MovieHistoryDTO movieHistoryDTO = new MovieHistoryDTO();
                    movieHistoryDTO.setMovieId(history.getMovie().getId());
                    movieHistoryDTO.setMovieName(history.getMovie().getName());
                    movieHistoryDTO.setPosterUrl(history.getMovie().getUrl());
                    movieHistoryDTO.setWatchedDate(history.getWatchedDate());
                    movieHistoryDTO.setRating(history.getRating());

                    return movieHistoryDTO;

                }).collect(Collectors.toList());
        userProfileDTO.setMovieHistories(movieHistories);

        return userProfileDTO;
    }
}
