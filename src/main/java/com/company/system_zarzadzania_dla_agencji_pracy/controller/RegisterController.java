package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.AgencyEmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
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
public class RegisterController {

    private UserDetailServiceImpl userDetailService;

    private static final String adminMail = "michal@admin.com";
    private EmployeeService employeeService;
    private EmployerService employerService;
    private AdministratorRepository administratorRepository;
    private UserRepository userRepository;
    private AgencyEmployeeService agencyEmployeeService;

    @Autowired
    public RegisterController(UserDetailServiceImpl userDetailService, EmployeeService employeeService, EmployerService employerService, AdministratorRepository administratorRepository, UserRepository userRepository, AgencyEmployeeService agencyEmployeeService) {
        this.userDetailService = userDetailService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.administratorRepository = administratorRepository;
        this.userRepository = userRepository;
        this.agencyEmployeeService = agencyEmployeeService;
    }


    @GetMapping("/register")
    public String getRegisterForm(){
        return "register";
    }

    @GetMapping("/register/pracownik")
    public String getEmployeeForm(Model model){
        EmployeeRQ employeeRQ = new EmployeeRQ();
        model.addAttribute("employeeRQ",employeeRQ);
        return "register-employee";
    }

    @PostMapping("/register/pracownik")
    public String newEmployee(@Valid @ModelAttribute("employeeRQ")  EmployeeRQ employeeRQ, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "register-employee";
        }

        Optional<User> userOpt = userRepository.findUserByMail(employeeRQ.getMail());
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail(adminMail);

        if(userOpt.isPresent()){                        //sprawdzenie czy nie istnieje user o takim mailu //TODO mozna wydzielic osobna funkcje tutaj
            String mail = userOpt.get().getMail();
            model.addAttribute("existedUsername",mail);
            return "register-employee";
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
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail(adminMail);

        if(userOpt.isPresent()){                        //sprawdzenie czy nie istnieje user o takim mailu //TODO mozna wydzielic osobna funkcje tutaj
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


    @GetMapping("/register/pracownikagencji")
    public String getAgencyEmployeeForm(Model model){
        AgencyEmployeeRQ agencyEmployeeRQ = new AgencyEmployeeRQ();
        model.addAttribute("agencyEmployeeRQ",agencyEmployeeRQ);
        return "register-agencyemployee";
    }

    @PostMapping("/register/pracownikagencji")
    public String newAgencyEmployee(@Valid @ModelAttribute("agencyEmployeeRQ") AgencyEmployeeRQ agencyEmployeeRQ, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "register-agencyemployee";
        }

        Optional<User> userOpt = userRepository.findUserByMail(agencyEmployeeRQ.getMail());
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail(adminMail);

        if(userOpt.isPresent()){                        //sprawdzenie czy nie istnieje user o takim mailu //TODO mozna wydzielic osobna funkcje tutaj
            String mail = userOpt.get().getMail();
            model.addAttribute("existedUsername",mail);
            return "register-agencyemployee";
        }


        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            agencyEmployeeService.addAgencyEmployee(agencyEmployeeRQ, administrator);
            return "redirect:/login";
        }
        //tutaj mozna dodac przerwa techniczna czy cos (bo wtedy nie ma admina)
        return "redirect:/register";
    }


}
