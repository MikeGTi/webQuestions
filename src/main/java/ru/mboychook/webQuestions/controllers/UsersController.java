package ru.mboychook.webQuestions.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mboychook.webQuestions.models.*;
import ru.mboychook.webQuestions.services.UsersService;


import java.util.*;
import java.util.stream.Collectors;

import static ru.mboychook.webQuestions.models.RoleEnum.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public String allUsers(Model model, @RequestParam(required = false) String userNamePart){
        List<User> users = new ArrayList<User>();
        if( userNamePart == null ) {
            usersService.findAll().forEach(users::add);
        } else {
            usersService.findUsersByPartName(userNamePart).forEach(users::add);
        }

        model.addAttribute("users", users);
        return "/users/index";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") UUID id,
                           Model model){
        model.addAttribute("user", usersService.findOne(id));
        return "/users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        //user.addRole(new Role((long) GUEST.getIndex(), GUEST));
        return "/users/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){ return "/users/new"; }
        usersService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{user_id}/edit")
    public String editUser(@NotNull Model model,
                           @PathVariable("user_id") String user_id){
        model.addAttribute("user", usersService.findOne(UUID.fromString(user_id)));
        return "/users/edit";
    }

    @PatchMapping("/{user_id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("user_id") String user_id){

        user.setRoles(new ArrayList<>(new HashSet<>(user.getRoles())));
        if (bindingResult.hasErrors()){ return "/users/edit"; }
        usersService.update(UUID.fromString(user_id), user);
        return "redirect:/users";
    }

    @DeleteMapping("/{user_id}")
    public String deleteUser(@PathVariable("user_id") String user_id){
        usersService.delete(UUID.fromString(user_id));
        return "redirect:/users";
    }

    // user roles field section
    @PatchMapping(value = "/{user_id}/edit", params={"addRole"})
    public String addRole(@ModelAttribute("user") User user,
                            @PathVariable("user_id") String user_id,
                            @RequestParam("addRole") Boolean addRole) {
        if (addRole){
            Role role = new Role(3L, GUEST);
            role.addUser(user);
            user.addRole(role);
        }
        return "/users/edit";
    }

    @PatchMapping(value = "/{user_id}/edit", params={"removeRole"})
    public String removeRole(@ModelAttribute("user") User user,
                               @PathVariable("user_id") String user_id,
                               @RequestParam("removeRole") String rolesListIndex) {
        //Role role = user.getRoles().get(Integer.parseInt(rolesListIndex));
        //role.removeUser(user);
        user.removeRole(Integer.parseInt(rolesListIndex));
        return "/users/edit";
    }

}
