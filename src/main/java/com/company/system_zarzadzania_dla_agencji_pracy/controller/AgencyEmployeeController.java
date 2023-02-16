package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.*;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
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
    private EmployerService employerService;

    @Autowired
    public AgencyEmployeeController(AgencyEmployeeService agencyEmployeeService, UserService userService, EmployerService employerService) {
        this.agencyEmployeeService = agencyEmployeeService;
        this.userService = userService;
        this.employerService = employerService;
    }

    @GetMapping("/home")
    public String getHomePageAgencyEmployee() {
        return "home-page";
    }

    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, RedirectAttributes redirectAttributes, Principal principal) {

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if (agencyEmployeeOpt.isPresent()) {
            AgencyEmployee agencyEmployee = agencyEmployeeOpt.get();
            model.addAttribute("agencyEmployee", agencyEmployee);
            return "agency-employee/agency-employee-account-details";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/lista-pracodawcow")
    public String showListOfAllEmployers(Model model, RedirectAttributes redirectAttributes, Principal principal) {

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());
        if (agencyEmployeeOpt.isPresent()) {
            List<Employer> employers = agencyEmployeeService.findAllEmployers();
            model.addAttribute("employers", employers);
            return "agency-employee/list-of-all-employers";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-pracodawcow/informacje-o-pracodawcy/{id}")
    public String getEmployerInfo(@PathVariable("id") Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if (agencyEmployeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
            return "redirect:/";
        }

        Optional<Employer> employerOpt = agencyEmployeeService.getEmployerById(id);
        if (employerOpt.isPresent()) {
            Date currDate = Date.valueOf(LocalDate.now());
            Employer employer = employerOpt.get();
            List<Order> orders = employerService.findAllOrdersByEmployerId(id);
            model.addAttribute("employer", employer);
            model.addAttribute("orders", orders);
            model.addAttribute("currDate", currDate);

            return "agency-employee/employer-details";
        }
        return "agency-employee/list-of-all-employers";
    }


    @GetMapping("/lista-pracodawcow/usun/{id}")
    public String rejectEmployerAccount(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if (agencyEmployeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
            return "redirect:/";
        }

        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            userService.deleteOwnAccount(userOpt.get().getIdUzytkownika());
            redirectAttributes.addFlashAttribute("deleteEmployerMessage", "Konto pracodawcy zostało pomyślnie usunięte");
            return "redirect:/pracownik-agencji/lista-pracodawcow";
        }
        redirectAttributes.addFlashAttribute("deleteEmployerErrorMessage", "Wystąpił błąd podczas usunięcia konta pracodawcy");
        return "redirect:/pracownik-agencji/lista-pracodawcow";
    }


    @GetMapping("/lista-wynagrodzen")
    public String getSalaryList(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());
        List<Salary> salaries = agencyEmployeeService.getAllSalaries();

        if (agencyEmployeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
            return "redirect:/";
        }
        model.addAttribute("salaries", salaries);
        return "agency-employee/salary-list";
    }

    @GetMapping("/lista-wynagrodzen/{id}")
    public String paySalary(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<AgencyEmployee> agencyEmployeeOpt = agencyEmployeeService.getAgencyEmployee(principal.getName());

        if (agencyEmployeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
            return "redirect:/";
        }

        Optional<Salary> salaryOpt = agencyEmployeeService.getSalaryById(id);

        if (salaryOpt.isPresent()) {
            Salary salary = salaryOpt.get();
            agencyEmployeeService.changeSalaryValueOnZero(salary, agencyEmployeeOpt.get());
            redirectAttributes.addFlashAttribute("paySalaryMessage", "Wynagrodzenie o numerze id równym " + salary.getIdPracownika() + " zostało przelane na konto pracownika");
            return "redirect:/pracownik-agencji/lista-wynagrodzen";
        }
        redirectAttributes.addFlashAttribute("paySalaryErrorMessage", "Wystąpił błąd podczas wykonywania przelewu na konto pracownika");
        return "redirect:/pracownik-agencji/lista-wynagrodzen";
    }
}
