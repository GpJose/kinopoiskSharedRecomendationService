package ru.kinoposisk.security.detail;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.RoleEnums;
import ru.kinoposisk.repository.UsersRepository;

import java.util.List;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String Login) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + Login);
        Users user = usersRepository.findByLogin(Login).orElseThrow(() -> new UsernameNotFoundException("User with login:"+Login+ " not found"));
        log.info("loadedUserByUsername: " + user);
        return new UserDetailImpl(user);
    }
}
