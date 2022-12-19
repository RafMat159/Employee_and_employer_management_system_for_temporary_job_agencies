package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usun-konto")
    public String deleteAccount(Principal principal, RedirectAttributes redirectAttributes){

        Optional<User> userOpt = userService.getUser(principal.getName());
        if(userOpt.isPresent()){
            userService.deleteOwnAccount(userOpt.get().getIdUzytkownika());
            redirectAttributes.addFlashAttribute("message","Twoje konto zostało pomyślnie usunięte");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Wystąpił błąd podczas usunięcia twojego konta");
        return "redirect:/login";
    }

}
