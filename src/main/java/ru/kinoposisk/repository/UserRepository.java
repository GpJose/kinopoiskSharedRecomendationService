package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByLogin(String login);

    Long deleteByLogin(String login);
    Optional<Users> findByEmail(String email);
}
