package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping( "/login")
    public String loginPage() {
//        User user = new User();
//        ModelAndView model = new ModelAndView();
//        model.addObject(user);

//        model.setViewName("login-form");
        return "login-form";
    }

//    @PostMapping("/login")
//    public String logInUser(@ModelAttribute("user") User user){
//
//    }






//
//    @PostMapping("/login")
//    public ModelAndView



//    @PostMapping(value = {"/login"})
//    public ModelAndView enterLoginData(@ModelAttribute User user){
//        userRepository.save(user);
//        return modelAndView;
//    }


//
//
//    @PostMapping("/login")
//    public String postLoginPage(@RequestBody UserLoginRQ userLoginRQ, ModelAndView modelAndView){
//        modelAndView.addObject("user","userLoginRQ");
//        return "login";
//    }


}
