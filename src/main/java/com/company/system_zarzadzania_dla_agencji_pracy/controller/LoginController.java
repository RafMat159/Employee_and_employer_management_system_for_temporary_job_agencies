package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @GetMapping(value = { "/login"})
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }



//    @GetMapping("/login")
//    public String getLoginPage(){
//        return "login";
//    }
//
//
//    @PostMapping("/login")
//    public String postLoginPage(@RequestBody UserLoginRQ userLoginRQ, ModelAndView modelAndView){
//        modelAndView.addObject("user","userLoginRQ");
//        return "login";
//    }


}
