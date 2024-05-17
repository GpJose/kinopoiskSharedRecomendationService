package ru.kinoposisk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users addUser(Users user) {
        return userRepository.save(user);
    }
}
