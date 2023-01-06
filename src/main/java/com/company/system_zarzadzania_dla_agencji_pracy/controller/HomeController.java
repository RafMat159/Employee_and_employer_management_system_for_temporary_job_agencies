package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {

    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(HomeController.class); //pozniej pododaje

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String welcomePage() {
        logger.info("Got request");
        return "index";
    }

    @GetMapping("/home")
    public String welcomePage(Principal principal) {

        Optional<User> userOpt = userRepository.findUserByMail(principal.getName());

        if(userOpt.isPresent()){
            User user = userOpt.get();
            switch(user.getRole()){
                case ADMINISTRATOR :
                    return "administrator/administrator-home-page";
                case PRACOWNIK:
                case PRACODAWCA:
                case PRACOWNIKAGENCJI:
                    return "home-page";
            }
        }
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

}
