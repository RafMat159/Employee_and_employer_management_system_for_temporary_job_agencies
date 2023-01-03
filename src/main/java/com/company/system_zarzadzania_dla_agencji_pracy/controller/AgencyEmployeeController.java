package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.*;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pracownik-agencji")
public class AgencyEmployeeController {

    private AgencyEmployeeService agencyEmployeeService;
    private UserService userService;

    @Autowired
    public AgencyEmployeeController(AgencyEmployeeService agencyEmployeeService, UserService userService) {
        this.agencyEmployeeService = agencyEmployeeService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePageAgencyEmployee(){
        return "home-page";
    }

    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal){

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if(agencyEmployeeOpt.isPresent()){
            AgencyEmployee agencyEmployee = agencyEmployeeOpt.get();
            model.addAttribute("agencyEmployee",agencyEmployee);
            return "agency-employee/agency-employee-account-details";
        }
        return "index";
    }


    @GetMapping("/lista-pracodawcow")
    public String showListOfAllEmployers(Model model,RedirectAttributes redirectAttributes ,Principal principal){

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());
        if(agencyEmployeeOpt.isPresent()) {
            List<Employer> employers = agencyEmployeeService.findAllEmployers();
            model.addAttribute("employers", employers);
            return "agency-employee/list-of-all-employers";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Twoje konto zostało usunięte, nie możesz wykonywać żadnych operacji.");
        return "index";
    }

    @GetMapping("/lista-pracodawcow/informacje-o-pracodawcy/{id}")
    public String getEmployerInfo(@PathVariable("id") Integer id, Model model, Principal principal){

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if(agencyEmployeeOpt.isEmpty()){
            return "index";
        }

        Optional<Employer> employerOpt = agencyEmployeeService.getEmployerById(id);
        if(employerOpt.isPresent()){
            Date currDate = Date.valueOf(LocalDate.now());
            Employer employer = employerOpt.get();
            List<Order> orders = employer.getOrders();
            model.addAttribute("employer",employer);
            model.addAttribute("orders",orders);
            model.addAttribute("currDate",currDate);

            return "agency-employee/employer-details";
        }
        return "agency-employee/list-of-all-employers";
    }


    @GetMapping("/lista-pracodawcow/usun/{id}")
    public String rejectEmployerAccount(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes){
        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if(agencyEmployeeOpt.isEmpty()){
            return "index";
        }

        Optional<User> userOpt = userService.getUserById(id);
        if(userOpt.isPresent()){
            userService.deleteOwnAccount(userOpt.get().getIdUzytkownika());
            redirectAttributes.addFlashAttribute("deleteEmployerMessage","Konto pracodawcy zostało pomyślnie usunięte");
            return "redirect:/pracownik-agencji/lista-pracodawcow";
        }
        redirectAttributes.addFlashAttribute("deleteEmployerErrorMessage","Wystąpił błąd podczas usunięcia konta pracodawcy");
        return "redirect:/pracownik-agencji/lista-pracodawcow";
    }

}
