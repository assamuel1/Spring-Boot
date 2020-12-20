package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private UsersService userService;
    private CredentialService credentialService;
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public CredentialController(UsersService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/addCredential")
    public String addNewCredential(@ModelAttribute("credential") Credentials credential, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Integer id = credential.getCredentialid();
        if (credential.getCredentialid() == null) {
            try {
                this.credentialService.createCredential(credential, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was successfully created.");
                credential.setUrl("");
                credential.setUsername("");
                credential.setPassword("");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Error encountered while creating your credential. Please try again");
                return "redirect:/result";
            }
        }else
            try {
                credentialService.updateCredential(credential, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was successfully edited");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Error encountered while creating your credential. Please try again");
                return "redirect:/result";
            }
    }
    @GetMapping("/deleteCredential/{credentialid}")
    public String deleteCredential(@PathVariable int credentialid, RedirectAttributes attributes) {
        try {
            credentialService.deleteCredential(credentialid);
            attributes.addFlashAttribute("successMessage", "Your credential was successfully deleted");
            return "redirect:/result";
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            attributes.addFlashAttribute("errorMessage", "Error encountered while creating your credential. Please try again");
            return "redirect:/result";
        }
    }
}

