package ru.mboychook.webQuestions.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.mboychook.webQuestions.models.User;
import ru.mboychook.webQuestions.services.UsersService;

@Component
public class UserValidator implements Validator {

    private UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String userName = user.getUsername();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userName.length() < 6 || userName.length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (usersService.loadUserByUsername(userName).getUsername().equals(userName)) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        if (usersService.loadUserByEmail(user.getEmail()).getUsername().equals(user.getEmail())) {
            errors.rejectValue("username", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

    }
}
