package com.liberty.doshka.validation;

import com.liberty.doshka.dao.UserDAO;
import com.liberty.doshka.form.UserForm;
import com.liberty.doshka.model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator{

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserForm userForm = (UserForm) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.userForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.userForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.userForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.userForm.confirmPassword");

        if (!this.emailValidator.isValid(userForm.getEmail())){
            //invalid email
            errors.rejectValue("email", "Pattern.userForm.email");
        } else if (userForm.getUserId() == null){
            User dbUser = userDAO.findUserByEmail(userForm.getEmail());
            if (dbUser !=null){
                //UserName is not available.
                errors.rejectValue("userName", "Duplicate.userForm.email");
            }
        }
        if (errors.hasFieldErrors("userName")){
            User dbUser = userDAO.findAppUserByUserName(userForm.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.userForm.userName");
            }
        }

        if (!errors.hasErrors()) {
            if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.userForm.confirmPassword");
            }
        }
    }

}
