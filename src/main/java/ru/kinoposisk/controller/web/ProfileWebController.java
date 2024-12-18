package ru.kinoposisk.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kinoposisk.dto.profile.UsersDTO;
import ru.kinoposisk.forms.FriendLoginForm;
import ru.kinoposisk.service.interfaces.FriendsService;
import ru.kinoposisk.service.interfaces.UsersService;

@Controller
public class ProfileWebController {
    private final UsersService usersService;
    private final FriendsService friendsService;

    @Autowired
    public ProfileWebController(UsersService usersService, FriendsService friendsService) {
        this.usersService = usersService;
        this.friendsService = friendsService;
    }

    @GetMapping("/profile")
    public String showProfileForm(Authentication authentication, Model model) {

        UsersDTO usersDTO = usersService.getProfile(usersService.findByLogin(authentication.getName()));
        model.addAttribute("listFriends", usersDTO.getFriends());
        model.addAttribute("listMovieHistory", usersDTO.getMovieHistories());
        model.addAttribute("friendLoginForm", new FriendLoginForm());

        return "profile";
    }
    @PostMapping("/profile/addFriends")
    public String addFriendsForm(@ModelAttribute("friendLoginForm") FriendLoginForm friendLoginForm, Authentication authentication, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/profile";
        }

        String friendLogin = friendLoginForm.getFriendLogin();
        friendsService.sendFriendRequest(usersService.findByLogin(authentication.getName()), usersService.findByLogin(friendLogin));
        return "redirect:/profile";
    }
}

