package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.OrderRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
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
@RequestMapping("/pracodawca")
public class EmployerController {

    private EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }


    @GetMapping("/home")
    public String getHomePageEmployer(){
        return "home-page";
    }

    @GetMapping("/dodaj-zlecenie-form")
    public String getOrderForm(Model model){
        OrderRQ orderRQ = new OrderRQ();
        model.addAttribute("orderRQ",orderRQ);
        return "employer/add-order";
    }

    @PostMapping("/dodaj-zlecenie")
    public String addNewOrder(@Valid @ModelAttribute("orderRQ") OrderRQ orderRQ, BindingResult bindingResult, Model model, Principal principal){

        if(bindingResult.hasErrors()){
            return "employer/add-order";
        }

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if(employerOpt.isPresent()){               //sprawdzenie czy istnieje pracodawca, jesli nie to nie mozna dodac zlecenia
            Employer employer = employerOpt.get();
            employerService.addNewOrder(orderRQ,employer);
            return "redirect:/pracodawca/dodaj-zlecenie-form";
        }

        return "employer/add-order";
    }

    @GetMapping("/lista-zlecen")
    public String showAllOrders(Model model, Principal principal){
        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if(employerOpt.isPresent()){
            Employer employer = employerOpt.get();
            List<Order> orders = employer.getOrders();
            model.addAttribute("orders",orders);
            return "employer/order-list-employer";
        }
        return "home-page";
    }


    @GetMapping("/lista-zlecen/usun/{id}")
    public String deleteOrder(@PathVariable("id") Integer id){
        employerService.deleteOrder(id);
        return "redirect:/pracodawca/lista-zlecen";
    }
//EDYTOWANIE
//    @GetMapping("/lista-zlecen/edytuj")
//    public String showEditFormOrder(Model model){
//        OrderRQ orderRQ = new OrderRQ();
//        model.addAttribute("orderRQ",orderRQ);
//        return "/employer/edit-order";
//    }

//    @PutMapping("/lista-zlecen/edytuj/{id}")
//    public String updateOrder(@PathVariable("id") Integer id, Mode)



    @GetMapping("/lista-dokumentow")
    public String getDocumentListEmployer(Model model, Principal principal){

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if(employerOpt.isPresent()){
            Employer employer = employerOpt.get();
            List<Document> documents = employer.getDocuments();
            model.addAttribute("documents",documents);
            return "documents-list";
        }
        return "home-page";
    }

    @GetMapping("/lista-dokumentow/{id}")
    public String getDocumentDetailsEmployer(@PathVariable("id") Integer idDokumentu,  Model model){

        Optional<Document> documentOpt = employerService.getDocumentEmployer(idDokumentu);

        if(documentOpt.isPresent()){               //sprawdzenie czy istnieje dany dokument
            Document document = documentOpt.get();
            model.addAttribute("content",document.getContent());
            model.addAttribute("documentType",document.getDocumentType());
            return "document-details";
        }
        return "documents-list";
    }



//    @GetMapping("/dodaj-dokument-form")
//    public String getDocumentForm(Model model){
//        DocumentRQ documentRQ = new DocumentRQ();
//        model.addAttribute("documentRQ",documentRQ);
//        return "add-document";
//    }

    @PostMapping("/dodaj-dokument")
    public String addNewOrderEmployer(@Valid @ModelAttribute("documentRQ") DocumentRQ documentRQ, BindingResult bindingResult, Model model, Principal principal){

        if(bindingResult.hasErrors()){
            return "add-document";
        }

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if(employerOpt.isPresent()){
            Employer employer = employerOpt.get();
            employerService.addNewDocumentEmployer(documentRQ,employer);
            return "redirect:/dodaj-dokument-form";
        }
        return "add-document";
    }


    @GetMapping("/lista-dokumentow/usun/{id}")
    public String deleteDocumentEmployer(@PathVariable("id") Integer id){
        employerService.deleteDocumentEmployer(id);
        return "redirect:/pracodawca/lista-dokumentow";
    }

    @GetMapping("/dane-uzytkownika")
    public String getAccountInformation(Model model, Principal principal){

        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());

        if(employerOpt.isPresent()){
            Employer employer = employerOpt.get();
            model.addAttribute("employer",employer);
            return "employer/employer-account-details";
        }
        return "index";
    }
}