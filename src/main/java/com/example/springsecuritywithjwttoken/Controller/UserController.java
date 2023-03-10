package com.example.springsecuritywithjwttoken.Controller;


import com.example.springsecuritywithjwttoken.entity.AuthUser;
import com.example.springsecuritywithjwttoken.entity.User;
import com.example.springsecuritywithjwttoken.service.UserService;
import com.example.springsecuritywithjwttoken.service.UserServiceToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class UserController {
    @Autowired
    UserServiceToken userServiceToken;
    @Autowired
    UserService userService;
    @GetMapping("/get")
    String method(){
        return "calling from unsecured method";
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    int method1(){
        return 4;
    }
    @GetMapping("/getBoolean")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    Boolean Method3(){
        return true;
    }
    @PostMapping("/token")
    public String authenticationToken(@RequestBody AuthUser authUser){
    return userServiceToken.generateToken(authUser.getUserName());
    }
    @PostMapping("/create")
    public User create(@RequestBody User user){
        return userService.create(user);
    }
}
