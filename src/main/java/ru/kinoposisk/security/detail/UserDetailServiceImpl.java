package ru.kinoposisk.security.detail;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.UserRepository;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String Login) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + Login);
        Users user = userRepository.findByLogin(Login).orElseThrow(() -> new UsernameNotFoundException("User with login:"+Login+ " not found"));
        log.info("loadedUserByUsername: " + user);
        return new UserDetailImpl(user);
    }
}
