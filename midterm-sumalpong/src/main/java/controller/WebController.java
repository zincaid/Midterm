package controller;

import com.google.api.services.people.v1.model.Person;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.GoogleContactsService;

@Controller
public class WebController {
    private final GoogleContactsService googleContactsService;

    public WebController(GoogleContactsService googleContactsService) {
        this.googleContactsService = googleContactsService;
    }

    @GetMapping({"/contacts"})
    public String showContacts(Model model) {
        try {
            List<Person> contacts = this.googleContactsService.getContacts();
            System.out.println("Fetched contacts: " + contacts.size());
            model.addAttribute("contacts", contacts);
            return "contacts";
        } catch (IOException var3) {
            IOException e = var3;
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch contacts.");
            return "error";
        }
    }

    @PostMapping({"/api/contacts/create"})
    public String createContact(@RequestParam String givenName, @RequestParam String familyName, @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber) throws IOException {
        Person newContact = this.googleContactsService.createContact(givenName, familyName, email, phoneNumber);
        System.out.println("Contact created: " + newContact.getResourceName());
        return "redirect:/contacts";
    }

    @PostMapping({"/api/contacts/update"})
    public String updateContact(@RequestParam String resourceName, @RequestParam String givenName, @RequestParam String familyName, @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber) {
        try {
            this.googleContactsService.updateContact(resourceName, givenName, familyName, email, phoneNumber);
            System.out.println("Contact updated: " + resourceName);
            return "redirect:/contacts";
        } catch (IOException var7) {
            IOException e = var7;
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/api/contacts/delete"})
    public String deleteContact(@RequestParam String resourceName) {
        try {
            this.googleContactsService.deleteContact(resourceName);
            System.out.println("Deleted contact: " + resourceName);
            return "redirect:/contacts";
        } catch (IOException var3) {
            IOException e = var3;
            e.printStackTrace();
            return "error";
        }
    }
}
