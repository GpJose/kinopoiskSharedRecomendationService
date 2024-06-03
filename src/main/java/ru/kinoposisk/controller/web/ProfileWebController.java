package ru.kinoposisk.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kinoposisk.dto.profile.UserProfileDTO;
import ru.kinoposisk.service.interfaces.UsersService;

@Controller
public class ProfileWebController {
    private final UsersService usersService;

    @Autowired
    public ProfileWebController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/profile")
    public String showProfileForm(Authentication authentication, Model model) {
        UserProfileDTO userProfileDTO = usersService.getProfile(usersService.findByLogin(authentication.getName()));
        model.addAttribute("listFriends", userProfileDTO.getFriends());
        model.addAttribute("listMovieHistory", userProfileDTO.getMovieHistories());
        return "profile";
    }
}
