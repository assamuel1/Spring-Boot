package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
//This class was provided by the tech lead in the base project file
public class AuthenticationService implements AuthenticationProvider {
        private UsersMapper userMapper;
        private HashService hashService;

        public AuthenticationService(UsersMapper userMapper, HashService hashService) {
            this.userMapper = userMapper;
            this.hashService = hashService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            Users user = userMapper.getUser(username);
            if (user != null) {
                String encodedSalt = user.getSalt();
                String hashedPassword = hashService.getHashedValue(password, encodedSalt);
                if (user.getPassword().equals(hashedPassword)) {
                    return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
                }
            }

            return null;
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    }

