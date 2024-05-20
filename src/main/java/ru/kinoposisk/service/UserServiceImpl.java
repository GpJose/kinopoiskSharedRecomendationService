package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dao.auth.AuthSignUpDAO;
import ru.kinoposisk.dao.changePass.ChangePassByEmailDAO;
import ru.kinoposisk.dao.changePass.ChangePassByLoginDAO;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.RoleEnums;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.UserService;

import java.io.IOException;
import java.util.*;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(AuthSignUpDAO user) {

        Users userEntity = Users.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
        add(userEntity);
    }

    @Override
    public Users add(Users users) {

        if (checkLoginIsNew(users.getLogin()) && checkEmailIsNew(users.getEmail())) {

            log.info("User with login:${} and email:${} has benn created successfully", users.getLogin(), users.getEmail());
            return userRepository.save(users);
        }
        else {
            throw new DuplicateKeyException("User with login: " + users.getLogin() + " or email: " + users.getEmail() + " already exist");
        }
    }

    @Override
    public void deleteByID(Long id) {

        if (userRepository.findById(id).isPresent()) {

            log.info("User with id:${} has been deleted", id);
            userRepository.deleteById(id);
        }
        else {
            throw new UsernameNotFoundException("User with id: "+ id + " not found");
        }
    }

    @Override
    public Long deleteByLogin(String login) {

        if (checkLoginIsNew(login)) {
            log.info("User with id:${} has been deleted", login);

            return userRepository.deleteByLogin(login);
        }
        else {
            throw new UsernameNotFoundException("User with login: "+login+ " not found");
        }
    }

    @Override
    public Users updateUser(Users user) {

        return userRepository.save(user);
    }
    @Override
    public Users usersBuilder(Long id, Friends friends) {

        Users users = findByID(id);
        users.getFriends().add(friends);
        log.info("Friends has been added to user with id:${} ", id);
        log.info("Friends: " + users.getFriends());

        return updateUser(users);
    }
    @Override
    public Users usersBuilder(Long id, QuizAnswers quizAnswers) {

        Users users = findByID(id);
        users.getQuizAnswers().add(quizAnswers);
        log.info("QuizAnswers has been added to user with id:${} ", id);
        log.info("QuizAnswers: " + users.getQuizAnswers());

        return updateUser(users);
    }

    @Override
    public Users usersBuilder(Long id, RoleEnums roleEnums) {

        Users users = findByID(id);
        List<RoleEnums> roles = new ArrayList<>();
        roles.add(roleEnums);
        users.setRoles(roles);
        log.info("Role has been changed to user with id:${} ", id);
        log.info("Role: " + users.getRoles());

        return updateUser(users);
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

        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Users findByLogin(String login) {

        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public Users findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public List<Users> getAll() {

        return userRepository.findAll();
    }

    @Override
    public void changePassword(ChangePassByLoginDAO changePassByLoginDAO) throws IOException {

        changePassword(
                findByLogin(changePassByLoginDAO.getLogin()),
                changePassByLoginDAO.getNewPassword(),
                changePassByLoginDAO.getOldPassword());
    }

    @Override
    public void changePassword(ChangePassByEmailDAO changePassByEmailDAO) throws IOException {

        changePassword(
                findByEmail(changePassByEmailDAO.getEmail()),
                changePassByEmailDAO.getNewPassword(),
                changePassByEmailDAO.getOldPassword());
    }

    private void changePassword(Users user, String newPassword, String oldPassword ) throws IOException {

        if (user.getPassword().equals(passwordEncoder.encode(oldPassword))) {

            newPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newPassword);
            updateUser(user);
            log.info("Password has been changed to user with login:${} email:${}", user.getLogin(), user.getEmail());
        }
        else {
            throw new IOException ("Old password is incorrect");
        }
    }
    private boolean checkEmailIsNew(String email) {

        return !userRepository.findByEmail(email).isPresent();
    }
    private Boolean checkLoginIsNew(String login) {

        return !userRepository.findByLogin(login).isPresent();
    }
}
