package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dao.auth.AuthSignUpDAO;
import ru.kinoposisk.dao.changePass.ChangePassByEmailDAO;
import ru.kinoposisk.dao.changePass.ChangePassByLoginDAO;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;

import java.io.IOException;

public interface UsersService extends BaseService<Users> {

    void add(AuthSignUpDAO user);
    Long deleteByLogin(String login);
    Users findByEmail(String email);
    Users updateUser(Users user);
    void changePassword(ChangePassByEmailDAO changePassByEmailDAO) throws IOException;
    void changePassword(ChangePassByLoginDAO changePassByLoginDAO) throws IOException;

    String getRole(Long id);
    String getRole(String login);
    Users findByLogin(String login);

    boolean changeActiveStatus(String login, Boolean isActive);
}
