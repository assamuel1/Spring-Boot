package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller

public class HomeController {

    private UsersService userService;
    private  NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;


    public HomeController(UsersService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String homePageView(@ModelAttribute("noteObject") Notes note , @ModelAttribute("credential") Credentials credential,  @ModelAttribute("Files") Files file, Model model, Authentication authentication) {
        //getusername to get the userid
        String username=authentication.getName();
        int userid = userService.getUser(username).getUserId();

        //get the files for the user and display it on the home page
        List<Files> files = this.fileService.getAllFiles(userid);
        model.addAttribute("files", files);

        //get the notes for the user and display it on the home page
        List<Notes> notes = this.noteService.getNotes(userid);
        model.addAttribute("notes", notes);

        //get the credentials for the user and display it on the home page
        List<Credentials> credentials = this.credentialService.getAllCredentials(userid);
        model.addAttribute("credentials", credentials);

        return "home";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}

