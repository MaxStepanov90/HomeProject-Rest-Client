package com.stepanov.documentclient.controller;

import com.stepanov.documentclient.model.Document;
import com.stepanov.documentclient.service.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private DocumentServiceImpl documentService;

    @Autowired
    public DocumentController(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public String getDocumentsList(Model model) {
        model.addAttribute("documents", documentService.getDocumentsList());
        return "documents";
    }

    @GetMapping("/{id}")
    public String getDocumentById(Model model, @PathVariable("id") long id) {
        model.addAttribute("document", documentService.getDocumentById(id));
        return "document";
    }

    @PostMapping("/save")
    public String saveDocument(Document document) {
        documentService.saveDocument(document);
        return "redirect:/documents";
    }
    @GetMapping("/delete/{id}")
    public String deleteDocumentById(@PathVariable("id") long id) {
        documentService.deleteDocumentById(id);
        return "redirect:/documents";
    }
}

