package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentController {

    DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/dodaj-dokument-form")
    public String getDocumentForm(Model model) {
        DocumentRQ documentRQ = new DocumentRQ();
        model.addAttribute("documentRQ", documentRQ);
        return "add-document";
    }
}
