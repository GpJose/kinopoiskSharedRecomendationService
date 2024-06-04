package ru.kinoposisk.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kinoposisk.forms.SetStatusForm;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.service.interfaces.AdminService;
import ru.kinoposisk.service.interfaces.UsersService;

@Controller
public class AdminWebController {
    private final UsersService usersService;
    private final AdminService adminService;

    public AdminWebController(UsersService usersService, AdminService adminService) {
        this.usersService = usersService;
        this.adminService = adminService;
    }

    @GetMapping(value = "/admin")
    public String showAdminForm(Model model) {
        model.addAttribute("allUsers",adminService.getAllUsers());
        model.addAttribute("status");
        model.addAttribute("userStatus", new SetStatusForm());
        model.addAttribute("friendLoginForm", new Users());
        return "admin";
    }
    @PostMapping(value = "/admin/userStatus")
    public String setUserStatusForm(@ModelAttribute("friendLoginForm")Users user) {
        adminService.blockUser(user.getLogin());
        return "redirect:/admin";
    }
}
