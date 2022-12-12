package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    private AdministratorRepository administratorRepository;    //na dana chwile zostawiam
//    private UserService userService;
    private AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorRepository administratorRepository, AdministratorService administratorService) {
        this.administratorRepository = administratorRepository;
//        this.userService = userService;
        this.administratorService = administratorService;
    }


    @GetMapping("/lista-uzytkownikow")
    public String findAllUsers(Model model){
        model.addAttribute("users",administratorService.findAllUsers());
        return "lista-uzytkownikow-form";
    }

    @GetMapping("/lista-uzytkownikow/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        administratorService.deleteUser(id);
        return "redirect:/administrator/lista-uzytkownikow";
    }

}
