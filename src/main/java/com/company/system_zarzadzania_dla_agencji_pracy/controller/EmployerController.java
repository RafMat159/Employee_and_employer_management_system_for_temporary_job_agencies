package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.OrderRQ;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pracodawca")
public class EmployerController {

    private EmployerService employerService;
    private EmployeeService employeeService;

    @Autowired
    public EmployerController(EmployerService employerService, EmployeeService employeeService) {
        this.employerService = employerService;
        this.employeeService = employeeService;
    }


    @GetMapping("/home")
    public String getHomePageEmployer() {
        return "home-page";
    }

    @GetMapping("/dodaj-zlecenie-form")
    public String getOrderForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            OrderRQ orderRQ = new OrderRQ();
            model.addAttribute("orderRQ", orderRQ);
            return "employer/add-order";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @PostMapping("/dodaj-zlecenie")
    public String addNewOrder(@Valid @ModelAttribute("orderRQ") OrderRQ orderRQ, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "employer/add-order";
        }

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {               //sprawdzenie czy istnieje pracodawca, jesli nie to nie mozna dodac zlecenia
            Employer employer = employerOpt.get();
            employerService.addNewOrder(orderRQ, employer);
            return "redirect:/pracodawca/dodaj-zlecenie-form";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-zlecen")
    public String showAllOrders(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {
            Date currDate = Date.valueOf(LocalDate.now());
            Employer employer = employerOpt.get();
            List<Order> orders = employer.getOrders();
            model.addAttribute("orders", orders);
            model.addAttribute("currDate", currDate);
            return "order-list";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/lista-zlecen/usun/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            employerService.deleteOrder(id);
            return "redirect:/pracodawca/lista-zlecen";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-dokumentow")
    public String getDocumentListEmployer(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {
            Employer employer = employerOpt.get();
            List<Document> documents = employer.getDocuments();
            model.addAttribute("documents", documents);
            return "documents-list";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-zlecen/historia-zlecen")
    public String getOnlyEmployeeOrderListHistory(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            Date currDate = Date.valueOf(LocalDate.now());
            Employer employer = employerOpt.get();
            List<Order> orders = employer.getOrders();
            model.addAttribute("orders", orders);
            model.addAttribute("currDate", currDate);
            return "/employer/order-list-hist";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/lista-dokumentow/{id}")
    public String getDocumentDetailsEmployer(@PathVariable("id") Integer idDokumentu, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            Optional<Document> documentOpt = employerService.getDocumentEmployer(idDokumentu);

            if (documentOpt.isPresent()) {               //sprawdzenie czy istnieje dany dokument
                Document document = documentOpt.get();
                model.addAttribute("content", document.getContent());
                model.addAttribute("documentType", document.getDocumentType());
                return "document-details";
            }
            return "documents-list";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @PostMapping("/dodaj-dokument")
    public String addNewOrderEmployer(@Valid @ModelAttribute("documentRQ") DocumentRQ documentRQ, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "add-document";
        }

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {
            Employer employer = employerOpt.get();
            employerService.addNewDocumentEmployer(documentRQ, employer);
            return "redirect:/dodaj-dokument-form";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/lista-dokumentow/usun/{id}")
    public String deleteDocumentEmployer(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            employerService.deleteDocumentEmployer(id);
            return "redirect:/pracodawca/lista-dokumentow";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {
            Employer employer = employerOpt.get();
            model.addAttribute("employer", employer);
            return "employer/employer-account-details";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-pracownikow")
    public String showListOfAllEmployees(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            List<Employee> employees = employerService.findAllEmployees();
            model.addAttribute("employees", employees);
            return "/employer/list-of-all-employees";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/lista-pracownikow/{id}")
    public String showEmployeeDetails(@PathVariable("id") Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            Optional<Employee> employeeOpt = employerService.getEmployeeById(id);
            if (employeeOpt.isPresent()) {
                Date currDate = Date.valueOf(LocalDate.now());
                Employee employee = employeeOpt.get();
                List<Order> orders = employee.getOrders().stream().sorted(Comparator.comparing(Order::getExecutionDate).reversed()).collect(Collectors.toList());
                model.addAttribute("employee", employee);
                model.addAttribute("orders", orders);
                model.addAttribute("currDate", currDate);
                return "/employer/employee-details";
            }
            return "/employer/list-of-all-employees";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/moja-lista-pracownikow")
    public String showCurrentlyEmployedEmployees(Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if (employerOpt.isPresent()) {
            Date currDate = Date.valueOf(LocalDate.now());
            List<Employee> employees = employerService.findCurrentlyEmployedEmployees(employerOpt.get().getIdUzytkownika(), currDate);
            model.addAttribute("employees", employees);
            return "employer/list-of-all-employed-employees";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/moja-lista-pracownikow/{id}")
    public String showCurrentlyEmployedEmployeeDetails(@PathVariable("id") Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            Optional<Employee> employeeOpt = employerService.getEmployeeById(id);
            if (employeeOpt.isPresent()) {
                Date currDate = Date.valueOf(LocalDate.now());
                Employee employee = employeeOpt.get();
                List<Order> orders = employee.getOrders().stream().sorted(Comparator.comparing(Order::getExecutionDate).reversed()).collect(Collectors.toList());
                model.addAttribute("employerMail", principal.getName());
                model.addAttribute("employee", employee);
                model.addAttribute("orders", orders);
                model.addAttribute("currDate", currDate);
                return "/employer/employed-employee-details";
            }
            return "/employer/list-of-all-employed-employees";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }


    @GetMapping("/moja-lista-pracownikow/zrezygnuj/{idUzytkownika}/{idZlecenia}")
    public String fireEmployee(@PathVariable("idUzytkownika") Integer idUzytkownika, @PathVariable("idZlecenia") Integer idZlecenia, Principal principal, RedirectAttributes redirectAttributes) {

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        Optional<Order> orderOpt = employerService.findOrderEmployer(idZlecenia);
        Optional<Employee> employeeOpt = employerService.getEmployeeById(idUzytkownika);
        if (employerOpt.isPresent()) {
            if (orderOpt.isPresent() && employeeOpt.isPresent()) {
                employeeService.substractSalary(orderOpt.get(), employeeOpt.get());
                employerService.removeEmployeeFromOrder(employerOpt.get(), orderOpt.get(), employeeOpt.get());    //zerwanie połączenia między pracownikiem, a zleceniem
                redirectAttributes.addFlashAttribute("deletingMessage", "Zrezygnowano z pracownika nr:" + employeeOpt.get().getIdUzytkownika() + " dla zlecenia nr:" + orderOpt.get().getIdZlecenia() + ". Jeśli z Twojego konta została pobrana opłata za tego pracownika, wszystkie środki zostaną zwrócone.");
                return "redirect:/pracodawca/moja-lista-pracownikow";
            }
            redirectAttributes.addFlashAttribute("deletingErrorMessage", "Zrezygnowanie z pracownika nie powiodło się! Konto pracownika lub zlecenie zostało usunięte!");
            return "redirect:/pracodawca/moja-lista-pracownikow";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych akcji, Twoje konto zostało usunięte!");
        return "redirect:/";
    }

    @GetMapping("/moje-biezace-koszty")
    public String getCurrentSalaryPage(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
        if (employerOpt.isPresent()) {
            Employer employer = employerOpt.get();
            employerService.refreshCurrentCosts(employer);
            model.addAttribute("currentCosts", employer.getCurrentCosts());
            return "employer/check-current-salary";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

}