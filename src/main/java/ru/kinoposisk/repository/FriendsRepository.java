package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kinoposisk.model.Friends;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

}