package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.AgencyEmployee;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/pracownik-agencji")
public class AgencyEmployeeController {
    private AgencyEmployeeService agencyEmployeeService;

    @Autowired
    public AgencyEmployeeController(AgencyEmployeeService agencyEmployeeService) {
        this.agencyEmployeeService = agencyEmployeeService;
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
}
