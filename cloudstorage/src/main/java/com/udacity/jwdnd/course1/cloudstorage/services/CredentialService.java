package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UsersMapper userMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UsersMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;

    }

    public void createCredential(Credentials newCredential, String username) {
        //Encrypt password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(),encodedKey);

        newCredential.setPassword(encryptedPassword);
        newCredential.setKey(encodedKey);
        newCredential.setUserid(userMapper.getUserIdByUsername(username));
        this.credentialMapper.createCredential(newCredential);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }

    public List<Credentials> getAllCredentials(Integer userid)
    {
        List<Credentials> credentialsList = credentialMapper.getAllCredentials(userid);
        for (Credentials credential: credentialsList) {
            credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
        }
        return credentialsList;

    }

    public void updateCredential(Credentials editCredential, String username) {
        //Encrypt password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(editCredential.getPassword(),encodedKey);

        editCredential.setPassword(encryptedPassword);
        editCredential.setKey(encodedKey);

        credentialMapper.updateCredentials(editCredential);
    }

    public void deleteCredential(int credentialid)
    {
        credentialMapper.deleteCredentials(credentialid);
    }

    public String decryptPassword(Credentials credential){
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }
}
