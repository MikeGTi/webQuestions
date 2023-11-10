/*
package ru.mboychook.webQuestions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mboychook.webQuestions.models.User;
import ru.mboychook.webQuestions.services.UsersService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "auth/admin";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id,
                           Model model){
        model.addAttribute("user", usersService.findOne(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "users/new";
    }



    @PostMapping("")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            usersService.delete(userId);
        }
        return "redirect:/admin";
    }
}*/
