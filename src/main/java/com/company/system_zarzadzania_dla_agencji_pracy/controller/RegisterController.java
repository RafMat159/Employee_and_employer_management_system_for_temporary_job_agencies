package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    private UserDetailServiceImpl userDetailService;

    @Autowired
    public RegisterController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        User user = new User();
        user.setMail("dom");
        user.setPassword("dom");
        user.setRole(Role.PRACOWNIK);
        userDetailService.addUser(user);
        return "register";
    }

//    @PostMapping("/register")
//    public String createUser(@RequestBody ){
//
//        return null;
//    }
}
