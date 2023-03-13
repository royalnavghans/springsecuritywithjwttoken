package com.example.springsecuritywithjwttoken.service;


import com.example.springsecuritywithjwttoken.Repo.UserRpo;
import com.example.springsecuritywithjwttoken.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRpo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> userDetails= userRepo.findByName(username);
      return userDetails.map(UserServiceDetails::new).orElseThrow(()->new UsernameNotFoundException("user name not found"));

    }


}
