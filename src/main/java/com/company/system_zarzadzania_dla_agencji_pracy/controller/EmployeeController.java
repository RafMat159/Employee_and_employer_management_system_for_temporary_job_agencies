package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployerRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pracownik")
public class EmployeeController {
    private EmployerRepository employerRepository;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployerRepository employerRepository, EmployeeService employeeService) {
        this.employerRepository = employerRepository;
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
    public String getOrderListEmployee(Model model){
        model.addAttribute("orders",employeeService.findAllOrders());
        return "order-list";
    }

    @GetMapping("/lista-zlecen/informacje-o-pracodawcy/{id}")
    public String getEmployerInfo(@PathVariable("id") Integer id, Model model){

        Optional<Employer> employerOpt = employeeService.getEmployerById(id);

        if(employerOpt.isPresent()){
            Employer employer = employerOpt.get();
            model.addAttribute("employer",employer);
            return "employee/employer-info";
        }
        return "order-list";
    }

}
