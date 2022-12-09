package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
//@RequestMapping("/register")
public class RegisterController {

    private UserDetailServiceImpl userDetailService;

    private EmployeeService employeeService;
    private EmployerService employerService;
    private AdministratorRepository administratorRepository;
    private UserRepository userRepository;

    @Autowired
    public RegisterController(UserDetailServiceImpl userDetailService, EmployeeService employeeService, EmployerService employerService, AdministratorRepository administratorRepository, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.administratorRepository = administratorRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/register")
    public String getRegisterForm(){
        return "register";
    }

    @GetMapping("/register/pracownik")
    public String getEmployeeForm(Model model){
        EmployeeRQ employeeRQ = new EmployeeRQ();
        model.addAttribute("employeeRQ",employeeRQ);
        return "register-ea";
    }

    @PostMapping("/register/pracownik")
    public String newEmployee(@Valid @ModelAttribute("employeeRQ")  EmployeeRQ employeeRQ, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "register-ea";
        }

        Optional<User> userOpt = userRepository.findUserByMail(employeeRQ.getMail());
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail("michal@admin.com");

        if(userOpt.isPresent()){                        //sprawdzenie czy nie istnieje user o takim mailu
            String mail = userOpt.get().getMail();
            model.addAttribute("existedUsername",mail);
            return "register-ea";
        }


        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            employeeService.addEmployee(employeeRQ, administrator);
            return "redirect:/login";
        }
        //tutaj mozna dodac przerwa techniczna czy cos (bo wtedy nie ma admina)
        return "redirect:/register";
    }

    @GetMapping("/register/pracodawca")
    public String getEmployerForm(Model model){
        EmployerRQ employerRQ = new EmployerRQ();
        model.addAttribute("employerRQ",employerRQ);
        return "register-employer";
    }

    @PostMapping("/register/pracodawca")
    public String newEmployer(@Valid @ModelAttribute("employerRQ") EmployerRQ employerRQ, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "register-employer";
        }

        Optional<User> userOpt = userRepository.findUserByMail(employerRQ.getMail());
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail("michal@admin.com");

        if(userOpt.isPresent()){                        //sprawdzenie czy nie istnieje user o takim mailu
            String mail = userOpt.get().getMail();
            model.addAttribute("existedUsername",mail);
            return "register-employer";
        }


        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            employerService.addEmployer(employerRQ, administrator);
            return "redirect:/login";
        }
        //tutaj mozna dodac przerwa techniczna czy cos (bo wtedy nie ma admina)
        return "redirect:/register";
    }

}
