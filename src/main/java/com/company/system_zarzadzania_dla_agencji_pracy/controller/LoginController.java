package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping( "/login")
    public String loginPage() {
        return "login-form";
    }
}
