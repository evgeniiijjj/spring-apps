package com.example.contactlist.controllers;

import com.example.contactlist.entities.Contact;
import com.example.contactlist.services.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class ContactListController {

    private final ContactRepository service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", service.getContacts());
        return "index.html";
    }

    @GetMapping("/save/{id}")
    public String showSaveForm(@PathVariable Integer id, Model model) {
        if (id > 0) {
            Optional<Contact> optional = service.findContactById(id);
            if (optional.isEmpty()) {
                return "redirect:/";
            }
            model.addAttribute("contact", optional.get());
            return "save";
        }
        model.addAttribute("contact", new Contact());
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Contact contact) {
        service.save(contact);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
