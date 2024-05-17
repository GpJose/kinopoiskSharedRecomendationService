package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Nullable
    Users findByLogin(String login);
}
