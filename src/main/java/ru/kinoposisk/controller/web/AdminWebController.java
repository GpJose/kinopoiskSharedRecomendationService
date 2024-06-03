package ru.kinoposisk.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebController {
    @GetMapping(value = "admin")
    public String showAdminForm() {
        return "admin";
    }
}
