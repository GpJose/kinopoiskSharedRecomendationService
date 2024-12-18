package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieHistoryRepository extends JpaRepository<MovieHistory, Long> {

    @Query(value = "select * from kinopoisk_dev_service.movie_history m where m.user_id =?1", nativeQuery = true)
    Optional<List<MovieHistory>> findByUser(Long id);

//    List<MovieHistory> findByUser_LoginAndMovie_LocalRatingNotNull(Users login);

    List<MovieHistory> findByUser_Login(String login);



    @Transactional
    @Modifying
    @Query("update MovieHistory m set m.rating = null where m.user = ?1")
    int deleteAllRatingsByUser(Users user);

    @Transactional
    @Modifying
    @Query("delete from MovieHistory m where m.user = :user and m.movie = :movie")
    void deleteByUserAndMovie(Users user, Movies movie);


}