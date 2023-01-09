package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.AgencyEmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AdministratorService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.AgencyEmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    private AdministratorService administratorService;
    private EmployeeService employeeService;
    private EmployerService employerService;
    private AgencyEmployeeService agencyEmployeeService;

    @Autowired
    public AdministratorController(AdministratorService administratorService, EmployeeService employeeService, EmployerService employerService, AgencyEmployeeService agencyEmployeeService) {
        this.administratorService = administratorService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.agencyEmployeeService = agencyEmployeeService;
    }

    @GetMapping("/home")
    public String getHomePageAdministrator() {
        return "administrator/administrator-home-page";
    }


    @GetMapping("/lista-uzytkownikow")
    public String findAllUsers(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            model.addAttribute("users", administratorService.findAllUsers());
            return "administrator/users-list-form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-uzytkownikow/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            administratorService.deleteUser(id);
            return "redirect:/administrator/lista-uzytkownikow";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/utworz-uzytkownika")
    public String getDecisionPanel(Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            return "administrator/user-creation-page";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/utworz-uzytkownika/nowy-pracownik")
    public String getEmployeeForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            EmployeeRQ employeeRQ = new EmployeeRQ();
            model.addAttribute("employeeRQ", employeeRQ);
            return "administrator/employee-creation-page";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @PostMapping("/nowy-pracownik")
    public String newEmployee(@Valid @ModelAttribute("employeeRQ") EmployeeRQ employeeRQ, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if (bindingResult.hasErrors()) {
            return "administrator/employee-creation-page";
        }
        if (administratorService.checkIfUserIsPresent(employeeRQ.getMail())) {
            model.addAttribute("existingUsername", employeeRQ.getMail());
            return "administrator/employee-creation-page";
        }

        if (administratorOpt.isPresent()) {
            Administrator administrator = administratorOpt.get();
            employeeService.addEmployee(employeeRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/utworz-uzytkownika/nowa-firma")
    public String getEmployerForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            EmployerRQ employerRQ = new EmployerRQ();
            model.addAttribute("employerRQ", employerRQ);
            return "administrator/employer-creation-page";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @PostMapping("/nowa-firma")
    public String newEmployer(@Valid @ModelAttribute("employerRQ") EmployerRQ employerRQ, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if (bindingResult.hasErrors()) {
            return "administrator/employer-creation-page";
        }
        if (administratorService.checkIfUserIsPresent(employerRQ.getMail())) {
            model.addAttribute("existingUsername", employerRQ.getMail());
            return "administrator/employer-creation-page";
        }

        if (administratorOpt.isPresent()) {
            Administrator administrator = administratorOpt.get();
            employerService.addEmployer(employerRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/utworz-uzytkownika/nowy-pracownik-agencji")
    public String getAgencyEmployeeForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());
        if (administratorOpt.isPresent()) {
            AgencyEmployeeRQ agencyEmployeeRQ = new AgencyEmployeeRQ();
            model.addAttribute("agencyEmployeeRQ", agencyEmployeeRQ);
            return "administrator/agency-employee-creation-page";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @PostMapping("/nowy-pracownik-agencji")
    public String newAgencyEmployee(@Valid @ModelAttribute("agencyEmployeeRQ") AgencyEmployeeRQ agencyEmployeeRQ, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if (bindingResult.hasErrors()) {
            return "administrator/agency-employee-creation-page";
        }
        if (administratorService.checkIfUserIsPresent(agencyEmployeeRQ.getMail())) {
            model.addAttribute("existingUsername", agencyEmployeeRQ.getMail());
            return "administrator/agency-employee-creation-page";
        }

        if (administratorOpt.isPresent()) {
            Administrator administrator = administratorOpt.get();
            agencyEmployeeService.addAgencyEmployee(agencyEmployeeRQ, administrator);
            return "redirect:/administrator/utworz-uzytkownika";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Administrator> administratorOpt = administratorService.checkIfAdministratorIsPresent(principal.getName());

        if (administratorOpt.isPresent()) {
            Administrator administrator = administratorOpt.get();
            model.addAttribute("administrator", administrator);
            return "administrator/administrator-account-details";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

}
