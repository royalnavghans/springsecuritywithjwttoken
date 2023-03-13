package com.example.springsecuritywithjwttoken.Controller;


import com.example.springsecuritywithjwttoken.entity.AuthUser;
import com.example.springsecuritywithjwttoken.entity.User;
import com.example.springsecuritywithjwttoken.service.ServiceImpl;
import com.example.springsecuritywithjwttoken.service.UserService;
import com.example.springsecuritywithjwttoken.service.UserServiceToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class UserController {
    @Autowired
    UserServiceToken userServiceToken;

    @Autowired
   ServiceImpl service;
@Autowired
    private AuthenticationManager authenticationManager;
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
        Authentication authenticate = authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authUser.getUserName(), authUser.getPassword())));
      if(authenticate.isAuthenticated()){
          return userServiceToken.generateToken(authUser.getUserName());
      }else{
          throw  new UsernameNotFoundException("User not found");
      }
    }
    @PostMapping("/create")
    public User create(@RequestBody User user){
        return service.create(user);
    }
}
