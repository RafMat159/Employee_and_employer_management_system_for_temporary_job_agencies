package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    private AdministratorRepository administratorRepository;    //na dana chwile zostawiam
    private UserService userService;

    @Autowired
    public AdministratorController(AdministratorRepository administratorRepository, UserService userService) {
        this.administratorRepository = administratorRepository;
        this.userService = userService;
    }


    @GetMapping("/lista-uzytkownikow")
    public String findAllUsers(Model model){
//        ModelAndView model = new ModelAndView("lista-uzytkownikow-form");
        model.addAttribute("users",userService.findAllUsers());
//        model.addObject("users",userService.findAllUsers());
        return "lista-uzytkownikow-form";
    }
}
