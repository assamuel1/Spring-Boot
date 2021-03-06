package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

        private UsersService userService;
        private FileService fileService;
        private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public FileController(UsersService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    //Code snippet is based on information provided here https://spring.io/guides/gs/uploading-files/
    public String uploadFile(@RequestParam("file") MultipartFile filetoUpload, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();

        System.out.println(filetoUpload.getSize());

       try {
            if (filetoUpload.getSize()!= 0 && !fileService.fileExists(filetoUpload) && filetoUpload.getSize() <=  20971520 ) {
                this.fileService.createFile(filetoUpload, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your file was successfully created.");
                return "redirect:/result";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Error encountered while creating your file. Please try again.");
                return "redirect:/result";
            }
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error encountered while adding your file. Please try again");

        }
        return "redirect:/result";
    }

    @GetMapping("/deleteFile/{fileid}")
    public String deleteFile(@PathVariable int fileid, RedirectAttributes attributes) {
        try {
            fileService.deleteFile(fileid);
            attributes.addFlashAttribute("successMessage", "Your file was successfully deleted");
            return "redirect:/result";
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            attributes.addFlashAttribute("errorMessage", "Error encountered while creating adding your file. Please try again");
            return "redirect:/result";
        }
    }

    @GetMapping("/viewFile/{fileid}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> viewFile(@PathVariable int fileid, RedirectAttributes attributes) {
        try {
            Files filetoview = fileService.getFileByFileid(fileid);

            return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filetoview.getFilename() + "\"").body(new ByteArrayResource(filetoview.getFiledata()));

        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}