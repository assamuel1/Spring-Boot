package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);
    private NoteService noteService;
    private UsersService userService;

    public NoteController(NoteService noteService,UsersService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/addNote")
        public String addNewNotes(@ModelAttribute("noteObject") Notes note, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Integer id = note.getnoteId();
        if ((note.getnoteId()) == null) {
            try {
                this.noteService.createNote(note, username);
               redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully created.");
                note.setNotetitle("");
                note.setNotedescription("");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
              redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note creation... Please try again.");
                return "redirect:/result";
            }
        }
        else{
            try {
                noteService.updateNote(note, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully edited");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with editing the note... Please try again.");
                return "redirect:/result";
            }

        }
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable ("noteId") Integer noteId, RedirectAttributes attributes) {
       try{
        noteService.deleteNote(noteId);
        attributes.addFlashAttribute("successMessage", "Your note was successfully deleted");
        return "redirect:/result";
    } catch (Exception e) {
           logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
           attributes.addFlashAttribute("errorMessage", "Something went wrong with the note deletion... Please try again.");
           return "redirect:/result";
    }
    }



}
