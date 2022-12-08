package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {

    private UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView welcomePage(Principal principal) {

        Optional<User> userOpt = userRepository.findUserByMail(principal.getName());
        ModelAndView model = new ModelAndView();
        User user = userOpt.get();
        model.setViewName("home-page");
        return model;
//        switch(user.getRole()){
//            case PRACOWNIK :
//                return "pracownik";
//            case :
//
//        }
    }
}
