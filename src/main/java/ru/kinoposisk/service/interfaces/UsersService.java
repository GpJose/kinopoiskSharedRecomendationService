package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dto.auth.AuthSignUpDTO;
import ru.kinoposisk.dto.changePass.ChangePassByEmailDTO;
import ru.kinoposisk.dto.changePass.ChangePassByLoginDTO;
import ru.kinoposisk.dto.profile.UsersDTO;
import ru.kinoposisk.model.Users;

import java.io.IOException;

public interface UsersService extends BaseService<Users> {

    void add(AuthSignUpDTO user);
    Long deleteByLogin(String login);
    Users findByEmail(String email);
    Users updateUser(Users user);
    void changePassword(ChangePassByEmailDTO changePassByEmailDTO) throws IOException;
    void changePassword(ChangePassByLoginDTO changePassByLoginDTO) throws IOException;

    String getRole(Long id);
    String getRole(String login);
    Users findByLogin(String login);

    boolean changeActiveStatus(String login, Boolean isActive);

    UsersDTO getProfile(Users user);
}
