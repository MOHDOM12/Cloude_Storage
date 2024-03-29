package com.example.NewPro_cloudeStorage.services;

import com.example.NewPro_cloudeStorage.mapper.UserMapper;

import com.example.NewPro_cloudeStorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }


    public int addUser(User user){

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);


        String hashedP = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt,
                hashedP, user.getFirstName(), user.getLastName()));

    }

    public boolean isUserExist(String username){
        return userMapper.getUser(username) == null;
    }

    public User getUser(String username){
        return userMapper.getUser(username);
    }
}

