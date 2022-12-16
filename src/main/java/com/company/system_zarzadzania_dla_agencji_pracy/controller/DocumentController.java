package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DocumentController {

    DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/dodaj-dokument-form")
    public String getDocumentForm(Model model){
        DocumentRQ documentRQ = new DocumentRQ();
        model.addAttribute("documentRQ",documentRQ);
        return "add-document";
    }

//    @GetMapping("/lista-dokumentow/usun/{id}")
//    public String deleteDocument(@PathVariable("id") Integer id, Authentication authentication){
//        authentication
//        documentRepository.deleteById(id);
//        return "redirect:/pracodawca/lista-dokumentow";
//    }

}
