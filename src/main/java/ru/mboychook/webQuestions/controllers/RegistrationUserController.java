package ru.mboychook.webQuestions.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mboychook.webQuestions.models.User;
import ru.mboychook.webQuestions.services.SecurityService;
import ru.mboychook.webQuestions.services.UsersService;
import ru.mboychook.webQuestions.util.UserValidator;

@Controller
@RequestMapping("/registration")
public class RegistrationUserController {

    private UsersService usersService;

    private SecurityService securityService;

    private UserValidator userValidator;

    @Autowired
    public RegistrationUserController(UsersService usersService, SecurityService securityService, UserValidator userValidator) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) { return "/registration"; }
        usersService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "/login";
    }

    /*@GetMapping({"/"})
    public String afterAuthPage(Model model) {
        return "/assessments/index";
    }*/

    //not defined /login POST controller, it is provided by Spring Security

}
