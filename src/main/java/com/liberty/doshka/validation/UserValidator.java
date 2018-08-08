package com.liberty.doshka.validation;

import com.liberty.doshka.form.UserForm;
import com.liberty.doshka.model.User;
import com.liberty.doshka.repository.UserRepository;
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
    UserRepository userRepository;

//    @Autowired
//    private UserService userService;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty.userForm.emailAddress");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.userForm.confirmPassword");

        if (!this.emailValidator.isValid(userForm.getEmailAddress())){
            //invalid emailAddress
            errors.rejectValue("emailAddress", "Pattern.userForm.emailAddress");
        } else if (userForm.getUserId() == null){
            User dbUser = userRepository.findOneByEmailAddress(userForm.getEmailAddress());
            if (dbUser !=null){
                //UserName is not available.
                errors.rejectValue("userName", "Duplicate.userForm.emailAddress");
            }
        }
        if (errors.hasFieldErrors("userName")){
            User dbUser = userRepository.findUserByUserName(userForm.getUserName());
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
