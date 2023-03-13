package com.example.springsecuritywithjwttoken.filter;

import com.example.springsecuritywithjwttoken.service.UserService;

import com.example.springsecuritywithjwttoken.service.UserServiceToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserServiceToken serviceToken;
    @Autowired
    UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeaders("Authorization").toString();
        String token=null;
        String username=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = serviceToken.extractUsername(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails details=userService.loadUserByUsername(username);

            if(serviceToken.validateToken(token,details)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
