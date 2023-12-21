package com.gmailFetcher.gmailService.Controllers;
import com.gmailFetcher.gmailService.Services.Email;
import com.gmailFetcher.gmailService.Services.GmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.*;


public class GmailController {
    private final GmailService gmailService;

    public GmailController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @GetMapping("/gmail/emails")
    public String getLatestEmails(Model model) {
        try {
            List<Email> emails = gmailService.getLatestEmails();
            model.addAttribute("emails", emails);
            return "emailList";
        } catch (IOException e) {
            model.addAttribute("error", "Error fetching emails");
            return "error";
        }
    }
}
