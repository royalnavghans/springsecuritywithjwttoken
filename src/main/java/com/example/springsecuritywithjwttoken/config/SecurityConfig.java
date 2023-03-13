package com.example.springsecuritywithjwttoken.config;


import com.example.springsecuritywithjwttoken.filter.JwtAuthTokenFilter;
import com.example.springsecuritywithjwttoken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

@Autowired
    private JwtAuthTokenFilter filter;
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
             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and().authenticationProvider(authenticationProvider()).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean
  public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
      daoAuthenticationProvider.setUserDetailsService(userDetailsService());
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
 return daoAuthenticationProvider;
 }
@Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return configuration.getAuthenticationManager();
 }
}
