package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dao.auth.AuthSignUpDAO;
import ru.kinoposisk.dao.changePass.ChangePassByEmailDAO;
import ru.kinoposisk.dao.changePass.ChangePassByLoginDAO;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.RoleEnums;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<Users> {

    void add(AuthSignUpDAO user);
    Long deleteByLogin(String login);
    Users findByEmail(String email);
    Users updateUser(Users user);
    Users usersBuilder(Long id, Friends friends);
    void changePassword(ChangePassByEmailDAO changePassByEmailDAO) throws IOException;
    void changePassword(ChangePassByLoginDAO changePassByLoginDAO) throws IOException;
    Users usersBuilder(Long id, QuizAnswers quizAnswers);
    Users usersBuilder(Long id, RoleEnums roleEnums);
    String getRole(Long id);
    String getRole(String login);
    Users findByLogin(String login);

}
