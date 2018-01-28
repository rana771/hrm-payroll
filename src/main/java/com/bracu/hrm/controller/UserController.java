package com.bracu.hrm.controller;
import com.bracu.hrm.cache.CacheService;
import com.bracu.hrm.model.User;
import com.bracu.hrm.service.EmployeeService;
import com.bracu.hrm.service.SecurityService;
import com.bracu.hrm.service.UserService;
import com.bracu.hrm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @Autowired
    CacheService cacheService;
    
    @RequestMapping(value = {"/", "/welcome","/index"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	
    	System.out.println(cacheService.getUser().getFullName());
    	System.out.println(cacheService.getCompnay().getName());
        return "user.ndex";
    }
    @ResponseBody
    @RequestMapping(value="/useracc/save",method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public String save(@RequestBody User user, BindingResult result,ModelMap model){
        String message;
        if(result.hasErrors()){
            //Employee employee =employeeService.findById(user.getId());
            //model.addAttribute("employee",employee);
            return "employee/accountsettings/accountsettings";
        }
        else{
                userService.save(user);
        }
        message = messageSource.getMessage("save.successful.message", new String[]{"User Accounts Settings ", user.getFullName()}, Locale.getDefault());
        return message;

    }

}
