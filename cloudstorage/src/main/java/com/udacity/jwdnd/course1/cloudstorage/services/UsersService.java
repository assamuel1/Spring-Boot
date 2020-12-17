package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UsersService {

        private final UsersMapper userMapper;
        private final HashService hashService;

        public UsersService(UsersMapper userMapper, HashService hashService) {
            this.userMapper = userMapper;
            this.hashService = hashService;
        }

        public boolean isUsernameAvailable(String username) {
            return userMapper.getUser(username) == null;
        }

        //The code snippet below is based on the snippet provided in the Project directions
        public int createUser(Users user) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
            return userMapper.insert(new Users(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
        }

        public Users getUser(String username) {
            return userMapper.getUser(username);
        }
    }

