package ru.kinoposisk.controller.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kinoposisk.dto.quiz.QuizDTO;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;

import javax.validation.Valid;

@Controller
@Log4j2
public class QuizWebController {

    private final QuizUsersAnswersService quizUsersAnswersService;

    @Autowired
    public QuizWebController(QuizUsersAnswersService quizUsersAnswersService) {
        this.quizUsersAnswersService = quizUsersAnswersService;
    }

    @GetMapping("/quiz")
    public String showQuizForm(Model model) {

        model.addAttribute("quizDTO", new QuizDTO());
        model.addAttribute("genreEnums", GenreEnums.values());
        model.addAttribute("countryEnums", CountryEnums.values());

        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(@Valid @ModelAttribute("quizDTO") QuizDTO quizDTO, BindingResult bindingResult, Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("genreEnums", GenreEnums.values());
            model.addAttribute("countryEnums", CountryEnums.values());

            return "quiz";
        }

        quizUsersAnswersService.add(quizUsersAnswersService.build(quizDTO, authentication.getName()));
        return "redirect:/";
    }
}
