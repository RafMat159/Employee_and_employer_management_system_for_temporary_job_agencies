package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.*;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;
    private AgencyEmployeeService agencyEmployeeService;
    private EmployeeService employeeService;

    public UserController(UserService userService, AgencyEmployeeService agencyEmployeeService, EmployeeService employeeService) {
        this.userService = userService;
        this.agencyEmployeeService = agencyEmployeeService;
        this.employeeService = employeeService;
    }

    @GetMapping("/usun-konto")
    public String deleteAccount(Principal principal, RedirectAttributes redirectAttributes){

        Optional<User> userOpt = userService.getUser(principal.getName());
        if(userOpt.isPresent()){
            if(userOpt.get().getRole() == Role.PRACOWNIKAGENCJI){
                Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());
                if (agencyEmployeeOpt.isPresent()){
                    agencyEmployeeService.breakingConnectionWithAgencyEmployee(agencyEmployeeOpt.get());
                }
            } else if (userOpt.get().getRole() == Role.PRACOWNIK) {
                Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
                if(employeeOpt.isPresent()) {
                    Employee employee = employeeOpt.get();
                    Set<Order> orders = employee.getOrders();
                    for (Order order : orders) {
                        employeeService.substractSalary(order, employee);
                    }
                }
            }
            userService.deleteOwnAccount(userOpt.get().getIdUzytkownika());
            redirectAttributes.addFlashAttribute("message","Twoje konto zostało pomyślnie usunięte");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Wystąpił błąd podczas usunięcia twojego konta");
        return "redirect:/login";
    }

}
