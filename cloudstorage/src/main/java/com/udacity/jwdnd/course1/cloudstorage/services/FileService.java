package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UsersMapper userMapper;
    Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(FileMapper fileMapper, UsersMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }
    @PostConstruct
    public void postConstruct() { System.out.println("Creating FileService bean");
    }

    public List<Files> getAllFiles(int userid) {
        return fileMapper.getAllFiles(userid);
    }

    public boolean fileExists(MultipartFile filetoUpload){
        if (this.fileMapper.getFilebyFileName(filetoUpload.getOriginalFilename()) == null)
            return false;
        else
            return true;
    }
    public void createFile(MultipartFile filetoUpload, String username) throws IOException {
        System.out.println(filetoUpload.getContentType());
        System.out.println(filetoUpload.getSize());
        System.out.println(filetoUpload.getOriginalFilename());
        System.out.println(filetoUpload.getName());
        System.out.println("Inside createFile");
        try{
            Files newFile = new Files(null,filetoUpload.getOriginalFilename(),filetoUpload.getContentType(), filetoUpload.getSize(), userMapper.getUserIdByUsername(username), filetoUpload.getBytes());
            this.fileMapper.insertFile(newFile);

        }catch(Exception e){
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
        }

    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }

    public Files getFileByFileid(int fileId) {
        return fileMapper.getFilebyFileid(fileId);
    }
}
