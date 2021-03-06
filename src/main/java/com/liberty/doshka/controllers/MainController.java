package com.liberty.doshka.controllers;

import com.liberty.doshka.dao.UserService;
import com.liberty.doshka.form.UserForm;
import com.liberty.doshka.model.User;
import com.liberty.doshka.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder){
        //Form target
        Object target = dataBinder.getTarget();
        if (target==null){
            return;
        }
        log.info("Target "+ target);
        if (target.getClass()== UserForm.class){
            dataBinder.setValidator(userValidator);
        }
    }

    @RequestMapping("/")
    public String viewHome(Model model) {
        return "welcomePage";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerSuccessfulPage";
    }

    // Show Register page.
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "registerPage";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(Model model, @ModelAttribute("UserForm") @Validated UserForm userForm,
                               BindingResult result, final RedirectAttributes redirectAttributes){
        //Validate result
        if (result.hasErrors()){
            return "registerPage";
        }
        User newUser = null;
        try{
            newUser = userService.createUser(userForm);
        } catch (Exception e){
            model.addAttribute("errorMessage", "Error" + e.getMessage());
            return "registerPage";
        }
        redirectAttributes.addFlashAttribute("flashUser", newUser);
        return "redirect:/registerSuccessful";
    }


}
