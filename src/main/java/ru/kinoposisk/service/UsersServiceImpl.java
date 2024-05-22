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
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.UsersRepository;
import ru.kinoposisk.service.interfaces.UsersService;

import java.io.IOException;
import java.util.List;

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
            return usersRepository.save(users);
        }
        else {
            throw new DuplicateKeyException("User with login: " + users.getLogin() + " or email: " + users.getEmail() + " already exist");
        }
    }

    @Override
    public void deleteByID(Long id) {

        if (usersRepository.findById(id).isPresent()) {

            usersRepository.deleteById(id);
            log.info("User with id:${} has been deleted", id);
        }
        else {
            throw new UsernameNotFoundException("User with id: "+ id + " not found");
        }
    }

    @Override
    public Long deleteByLogin(String login) {

        if (checkLoginIsNew(login)) {
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

    @Override
    public boolean changeActiveStatus(String login, Boolean isActive) {

        return usersRepository.changeActiveStatus(findByLogin(login).getLogin(), isActive);
    }

    private boolean checkEmailIsNew(String email) {

        return !usersRepository.findByEmail(email).isPresent();
    }

    private boolean checkLoginIsNew(String login) {

        return !usersRepository.findByLogin(login).isPresent();
    }

    private boolean checkUserIdIsNew(Long userId) {

        return !usersRepository.findById(userId).isPresent();
    }
}
