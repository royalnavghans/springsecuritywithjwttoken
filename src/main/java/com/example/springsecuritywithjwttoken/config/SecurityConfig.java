package com.example.springsecuritywithjwttoken.config;


import com.example.springsecuritywithjwttoken.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
 @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin= User.withUsername("user").password(passwordEncoder.encode("password")).roles("ADMIN").build();
//     UserDetails emp= User.withUsername("user1").password(passwordEncoder.encode("password1")).roles("USER").build();

return new UserService();
    }
    @Bean
 public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
 }

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//cross site request forgery
    return httpSecurity.csrf().disable()
             .authorizeHttpRequests().requestMatchers("/demo/get","/demo/create","/demo/token")
             .permitAll()
             .and()
             .authorizeHttpRequests().requestMatchers("/demo/**")
             .authenticated().and()
             .formLogin().and()
             .build();
 }
 @Bean
  public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
      daoAuthenticationProvider.setUserDetailsService(userDetailsService());
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
 return daoAuthenticationProvider;
 }
}
