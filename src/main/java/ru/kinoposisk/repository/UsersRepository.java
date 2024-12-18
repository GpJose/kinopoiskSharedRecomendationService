package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kinoposisk.model.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    @Query(value = "SELECT * FROM kinopoisk_dev_service.users us WHERE us.login = ?1", nativeQuery = true)
    Optional<Users> findUserByLogin(String login);



    Long deleteByLogin(String login);
    Optional<Users> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Users u set u.active = ?2 where u.login = ?1")
    Boolean changeActiveStatus(String login, Boolean active);

}
