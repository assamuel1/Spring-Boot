package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteModalForm;
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
//@RequestMapping("/note")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);
    private NoteService noteService;
    private UsersService userService;

    public NoteController(NoteService noteService,UsersService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/result")
        public String addNewNotes(NoteModalForm noteModalForm, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        String id = noteModalForm.getNoteId();
        if ((noteModalForm.getNoteId()) == "") {
            try {
                this.noteService.createNote(noteModalForm, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully created.");
                noteModalForm.setNoteTitle("");
                noteModalForm.setNoteDescription("");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note creation... Please try again.");
                return "redirect:/result";
            }

        }
        else{
            try {
                noteService.updateNote(noteModalForm, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully edited");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with editing the note... Please try again.");
                return "redirect:/result";
            }

        }
    }


        @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable int noteid, RedirectAttributes attributes) {
       try{
        noteService.deleteNote(noteid);
        attributes.addFlashAttribute("successMessage", "Your note was successfully deleted");
        return "redirect:/result";
    } catch (Exception e) {
           logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
           attributes.addFlashAttribute("errorMessage", "Something went wrong with the note deletion... Please try again.");
           return "redirect:/result";
    }
    }



}
