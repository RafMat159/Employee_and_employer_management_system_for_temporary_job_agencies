package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.AgencyEmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AdministratorService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    private AdministratorRepository administratorRepository;    //na dana chwile zostawiam
//    private UserService userService;
    private AdministratorService administratorService;
    private EmployeeService employeeService;
    private EmployerService employerService;
    private AgencyEmployeeService agencyEmployeeService;

    @Autowired
    public AdministratorController(AdministratorRepository administratorRepository, AdministratorService administratorService, EmployeeService employeeService, EmployerService employerService, AgencyEmployeeService agencyEmployeeService) {
        this.administratorRepository = administratorRepository;
//        this.userService = userService;
        this.administratorService = administratorService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.agencyEmployeeService = agencyEmployeeService;
    }

    @GetMapping("/home")
    public String getHomePageAdministrator(){
        return "administrator/administrator-home-page";
    }


    @GetMapping("/lista-uzytkownikow")
    public String findAllUsers(Model model){
        model.addAttribute("users",administratorService.findAllUsers());
        return "administrator/users-list-form";
    }

    @GetMapping("/lista-uzytkownikow/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        administratorService.deleteUser(id);
        return "redirect:/administrator/lista-uzytkownikow";
    }

    @GetMapping("/utworz-uzytkownika")
    public String getDecisionPanel(){
        return "administrator/user-creation-page";
    }

    @GetMapping("/utworz-uzytkownika/nowy-pracownik")
    public String getEmployeeForm(Model model){
        EmployeeRQ employeeRQ = new EmployeeRQ();
        model.addAttribute("employeeRQ",employeeRQ);
        return "administrator/employee-creation-page";
    }

    @PostMapping("/nowy-pracownik")
    public String newEmployee(@Valid @ModelAttribute("employeeRQ")  EmployeeRQ employeeRQ, BindingResult bindingResult, Model model, Principal principal){

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if(bindingResult.hasErrors()){
            return "administrator/user-creation-page";
        }
        if(administratorService.checkIfUserIsPresent(employeeRQ.getMail())) {
            model.addAttribute("existingUsername", employeeRQ.getMail());
            return "administrator/employee-creation-page";
        }

        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            employeeService.addEmployee(employeeRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        return "administrator/user-creation-page";
    }

    @GetMapping("/utworz-uzytkownika/nowa-firma")
    public String getEmployerForm(Model model){
        EmployerRQ employerRQ = new EmployerRQ();
        model.addAttribute("employerRQ",employerRQ);
        return "administrator/employer-creation-page";
    }

    @PostMapping("/nowa-firma")
    public String newEmployee(@Valid @ModelAttribute("employerRQ")  EmployerRQ employerRQ, BindingResult bindingResult, Model model, Principal principal){

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if(bindingResult.hasErrors()){
            return "administrator/employer-creation-page";
        }
        if(administratorService.checkIfUserIsPresent(employerRQ.getMail())) {
            model.addAttribute("existingUsername", employerRQ.getMail());
            return "administrator/employer-creation-page";
        }

        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            employerService.addEmployer(employerRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        return "administrator/user-creation-page";
    }


    @GetMapping("/utworz-uzytkownika/nowy-pracownik-agencji")
    public String getAgencyEmployerForm(Model model){
        AgencyEmployeeRQ agencyEmployeeRQ = new AgencyEmployeeRQ();
        model.addAttribute("agencyEmployeeRQ",agencyEmployeeRQ);
        return "administrator/agency-employee-creation-page";
    }

    @PostMapping("/nowy-pracownik-agencji")
    public String newEmployee(@Valid @ModelAttribute("agencyEmployeeRQ")  AgencyEmployeeRQ agencyEmployeeRQ, BindingResult bindingResult, Model model, Principal principal){

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if(bindingResult.hasErrors()){
            return "administrator/agency-employee-creation-page";
        }
        if(administratorService.checkIfUserIsPresent(agencyEmployeeRQ.getMail())) {
            model.addAttribute("existingUsername", agencyEmployeeRQ.getMail());
            return "administrator/agency-employee-creation-page";
        }

        if(administratorOpt.isPresent()){               //sprawdzenie czy istnieje administrator do zarzadzania systemem, jesli nie to nie mozna sie zarejestrowac
            Administrator administrator = administratorOpt.get();
            agencyEmployeeService.addAgencyEmployee(agencyEmployeeRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        return "administrator/user-creation-page";
    }



    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal){

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if(administratorOpt.isPresent()){
            Administrator administrator = administratorOpt.get();
            model.addAttribute("administrator",administrator);
            return "administrator/administrator-account-details";
        }
        return "index";
    }

}
