package com.company.system_zarzadzania_dla_agencji_pracy.kontoler;

import com.company.system_zarzadzania_dla_agencji_pracy.model.request.UserRegisterRQ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserRegisterRQ userRegisterRQ){
        return null;
    }
}
