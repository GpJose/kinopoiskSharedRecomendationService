package ru.kinoposisk.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.service.interfaces.AdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/")
@Validated
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<Users>> getAllUsers() {

        return ResponseEntity.ok(adminService.getAllUsers());
    }
    @GetMapping(value = "users/{login}")
    public ResponseEntity<Users> getAllUsers(@PathVariable String login) {

        return ResponseEntity.ok(adminService.getUser(login));
    }

    @GetMapping(value = "users/{login}/history")
    public ResponseEntity<List<MovieHistory>> getAllHistoryByUsername(@PathVariable String login) {

        return ResponseEntity.ok(adminService.getAllHistoryByUsername(login));
    }

    @GetMapping(value = "users/{login}/ratings")
    public ResponseEntity<List<MovieHistory>> getAllRatingsByUsername(@PathVariable String login) {

        return ResponseEntity.ok(adminService.getAllRatingsByUsername(login));
    }

    @GetMapping(value = "users/{login}/answers")
    public ResponseEntity<List<QuizAnswers>> getAllAnswersByUsername(@PathVariable String login) {

        return ResponseEntity.ok(adminService.getAllAnswersByUsername(login));
    }

    @GetMapping(value = "users/{login}/block")
    public ResponseEntity<Boolean> getBlockUser(@PathVariable String login) {

        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUser(login).isActive());
    }
    @PutMapping(value = "users/{login}/block")
    public ResponseEntity<Boolean> blockUser(@PathVariable String login) {

        return ResponseEntity.status(HttpStatus.OK).body(adminService.blockUser(login));
    }
    @DeleteMapping(value = "users/{login}/ratings")
    public ResponseEntity<Integer> deleteAllUserRating(@PathVariable String login) {

        return new ResponseEntity<>(adminService.deleteAllRatingByUser(login),HttpStatus.OK);
    }
    @DeleteMapping(value = "users/{login}/ratings/{movieId}")
    public ResponseEntity<Void> deleteUserRating(@PathVariable String login, @PathVariable Long movieId) {

        adminService.deleteMovieRatingByUser(login, movieId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
