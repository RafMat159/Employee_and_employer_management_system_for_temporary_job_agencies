package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/pracownik")
public class EmployeeController {
    //private EmployerRepository employerRepository;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(//EmployerRepository employerRepository
                               EmployeeService employeeService) {
        //this.employerRepository = employerRepository;
        this.employeeService = employeeService;
    }


    @GetMapping("/home")
    public String getHomePageEmployee(){
        return "home-page";
    };

    @GetMapping("/lista-dokumentow")
    public String getDocumentListEmployee(Model model, Principal principal){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            List<Document> documents = employee.getDocuments();
            model.addAttribute("documents",documents);
            return "documents-list";
        }
        return "home-page";
    }

    @GetMapping("/lista-dokumentow/{id}")
    public String getDocumentDetails(@PathVariable("id") Integer idDokumentu, Model model){

        Optional<Document> documentOpt = employeeService.getDocumentEmployee(idDokumentu);

        if(documentOpt.isPresent()){               //sprawdzenie czy istnieje dany dokument
            Document document = documentOpt.get();
            model.addAttribute("content",document.getContent());
            model.addAttribute("documentType",document.getDocumentType());
            return "document-details";
        }
        return "documents-list";
    }

    @PostMapping("/dodaj-dokument")
    public String addNewOrder(@Valid @ModelAttribute("documentRQ") DocumentRQ documentRQ, BindingResult bindingResult, Model model, Principal principal){

        if(bindingResult.hasErrors()){
            return "add-document";
        }

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            employeeService.addNewDocumentEmployee(documentRQ,employee);
            return "redirect:/dodaj-dokument-form";
        }
        return "add-document";
    }


    @GetMapping("/lista-dokumentow/usun/{id}")
    public String deleteDocumentEmployee(@PathVariable("id") Integer id){
        employeeService.deleteDocumentEmployee(id);
        return "redirect:/pracownik/lista-dokumentow";
    }


    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            model.addAttribute("employee",employee);
            return "employee/employee-account-details";
        }
        return "index";
    }

    @GetMapping("/lista-zlecen")
    public String getWholeOrderListEmployee(Model model,Principal principal){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        if(employeeOpt.isPresent()){
            Date currDate = Date.valueOf(LocalDate.now());
            Employee employee = employeeOpt.get();
            model.addAttribute("employee",employee);
            model.addAttribute("orders",employeeService.findAllOrders());
            model.addAttribute("currDate",currDate);
            return "order-list";
        }
        return "index";
    }

    @GetMapping("/moja-lista-zlecen")
    public String getOnlyEmployeeOrderList(Model model,Principal principal){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        if(employeeOpt.isPresent()){
            Date currDate = Date.valueOf(LocalDate.now());
            Employee employee = employeeOpt.get();
            Set<Order> orders = employee.getOrders();
            model.addAttribute("orders",orders);
            model.addAttribute("currDate",currDate);
            return "/employee/employee-order-list";
        }
        return "index";
    }

    @GetMapping("/moja-lista-zlecen/historia-zlecen")
    public String getOnlyEmployeeOrderListHistory(Model model,Principal principal){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        if(employeeOpt.isPresent()){
            Date currDate = Date.valueOf(LocalDate.now());
            Employee employee = employeeOpt.get();
            Set<Order> orders = employee.getOrders();
            model.addAttribute("orders",orders);
            model.addAttribute("currDate",currDate);
            return "/employee/employee-order-list-hist";
        }
        return "index";
    }


    @GetMapping("/lista-zlecen/informacje-o-pracodawcy/{id}")
    public String getEmployerInfo(@PathVariable("id") Integer id, Model model,Principal principal){

        Optional<Employer> employerOpt = employeeService.getEmployerById(id);
        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());

        if(!(employeeOpt.isPresent())){
            return "index";
        }
        if(employerOpt.isPresent()){
            Date currDate = Date.valueOf(LocalDate.now());
            Employer employer = employerOpt.get();
            List<Order> orders = employer.getOrders();
            model.addAttribute("employer",employer);
            model.addAttribute("orders",orders);
            model.addAttribute("currDate",currDate);

            return "employee/employer-info";
        }
        return "order-list";
    }

    @GetMapping("/lista-zlecen/zapisz/{id}")
    public String signUpForOrder(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes, Model model){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        Optional<Order> orderOpt = employeeService.findOrderEmployee(id);

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            if(orderOpt.isPresent()){
                Order order = orderOpt.get();
                employeeService.addSalary(order,employee);
                employeeService.saveOrder(order,employee); //zapisywanie w bazie danych że dany pracownik zapisuje się na zlecenie
                redirectAttributes.addFlashAttribute("savingMessage","Zapisano na zlecenie nr:" + order.getIdZlecenia());
                return "redirect:/pracownik/lista-zlecen";
            }
            redirectAttributes.addFlashAttribute("savingErrorMessage","Dodanie na zlecenie nie powiodło się! Zlecenie zostało usunięte!");
            return "redirect:/pracownik/lista-zlecen";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Nie możesz wykonywać żadnych akcji, Twoje konto zostało usunięte!");
        return "redirect:/";
    }

    @GetMapping("/lista-zlecen/zrezygnuj/{id}")
    public String cancelOrderEmployee(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes, Model model){

        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        Optional<Order> orderOpt = employeeService.findOrderEmployee(id);

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            if(orderOpt.isPresent()){
                Order order = orderOpt.get();
                employeeService.substractSalary(order,employee);
                employeeService.removeOrderEmployee(order,employee);    //zerwanie połączenia między pracownikiem, a zleceniem
                redirectAttributes.addFlashAttribute("deletingMessage","Zrezygnowano ze zlecenia nr:" + order.getIdZlecenia() +  ". Jeśli kwota za to zlecenie została przelana na Twoje konto, będziesz musiał dokonać zwrotu pieniędzy.");
                return "redirect:/pracownik/moja-lista-zlecen";
            }
            redirectAttributes.addFlashAttribute("deletingErrorMessage","Zrezygnowanie ze zlecenia nie powiodło się! Zlecenie zostało usunięte!");
            return "redirect:/pracownik/moja-lista-zlecen";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Nie możesz wykonywać żadnych akcji, Twoje konto zostało usunięte!");
        return "redirect:/";
    }


    @GetMapping("/moje-wynagrodzenie")
    public String getSalaryPage(Model model, Principal principal,RedirectAttributes redirectAttributes){
        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            model.addAttribute("salary",employee.getSalary());
            model.addAttribute("isStudent",employee.isStudentStatus());
            return "employee/check-salary";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }

    @GetMapping("/moje-wynagrodzenie/ureguluj-wynagrodzenie")
    public String settleSalary(Principal principal, RedirectAttributes redirectAttributes){
        Optional<Employee> employeeOpt = employeeService.getEmployee(principal.getName());
        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            employeeService.settleSalary(employee.getSalary());
            return "redirect:/pracownik/moje-wynagrodzenie";
        }
        redirectAttributes.addFlashAttribute("errorMessage","Nie możesz wykonywać żadnych działań, Twoje konto zostało usunięte.");
        return "redirect:/";
    }
}
