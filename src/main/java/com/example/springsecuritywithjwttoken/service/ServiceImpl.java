package com.example.springsecuritywithjwttoken.service;

import com.example.springsecuritywithjwttoken.Repo.UserRpo;
import com.example.springsecuritywithjwttoken.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl {




    @Autowired
    UserRpo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;



    public User create(User user) {
        user.setPass((passwordEncoder.encode(user.getPass())));
        return userRepo.save(user);
    }
}
