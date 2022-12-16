//package com.company.system_zarzadzania_dla_agencji_pracy.controller;
//
//import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
//import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
//import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
//import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/pracownik")
//public class EmployeeController {
//    private EmployerRepository employerRepository;
//
//    @Autowired
//    public EmployeeController(EmployerRepository employerRepository) {
//        this.employerRepository = employerRepository;
//    }
//
//
//    @GetMapping("/home")
//    public String getHomePageEmployee(){
//        return "home-page";
//    };
//
//    @GetMapping("/lista-dokumentow")
//    public String getDocumentListEmployer(Model model, Principal principal){
//
//        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
//
//        if(employerOpt.isPresent()){               //sprawdzenie czy istnieje pracodawca, jesli nie to nie mozna dodac zlecenia
//            Employer employer = employerOpt.get();
//            List<Document> documents = employer.getDocuments();
//            model.addAttribute("documents",documents);
//            return "documents-list";
//        }
//        return "home-page";
//    }
//
//    @GetMapping("/lista-dokumentow/{id}")
//    public String getDocumentDetails(@PathVariable("id") Integer idDokumentu, Model model){
//
//        Optional<Document> documentOpt = employerService.getDocument(idDokumentu);
//
//        if(documentOpt.isPresent()){               //sprawdzenie czy istnieje dany dokument
//            Document document = documentOpt.get();
//            model.addAttribute("content",document.getContent());
//            model.addAttribute("documentType",document.getDocumentType());
//            return "document-details";
//        }
//        return "documents-list";
//    }
//
//
//
//
//
//    @PostMapping("/dodaj-dokument")
//    public String addNewOrder(@Valid @ModelAttribute("documentRQ") DocumentRQ documentRQ, BindingResult bindingResult, Model model, Principal principal){
//
//        if(bindingResult.hasErrors()){
//            return "add-document";
//        }
//
//        Optional<Employer> employerOpt = employerService.getEmployer(principal.getName());
//
//        if(employerOpt.isPresent()){               //sprawdzenie czy istnieje pracodawca, jesli nie to nie mozna dodac zlecenia
//            Employer employer = employerOpt.get();
//            employerService.addNewDocumentEmployer(documentRQ,employer);
//            return "redirect:/pracodawca/dodaj-dokument-form";
//        }
//        return "add-document";
//    }
//
//
//    @GetMapping("/lista-dokumentow/usun/{id}")
//    public String deleteDocument(@PathVariable("id") Integer id){
//        employerService.deleteDocument(id);
//        return "redirect:/pracodawca/lista-dokumentow";
//    }
//
//
//}
