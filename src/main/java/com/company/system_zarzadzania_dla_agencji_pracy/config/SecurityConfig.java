package com.company.system_zarzadzania_dla_agencji_pracy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(requests -> requests
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/img/**", "/styles/**").permitAll()
                .mvcMatchers("/static/**").permitAll()
                .mvcMatchers("/register/**").permitAll()
                .mvcMatchers("/pracownik/**").hasAnyRole("PRACOWNIK")
                .mvcMatchers("/pracownikagencji/**").hasAnyRole("PRACOWNIKAGENCJI")
                .mvcMatchers("/pracodawca/**").hasAnyRole("PRACODAWCA")
                .mvcMatchers("/administrator/**").hasRole("ADMINISTRATOR")
                .anyRequest().authenticated()
        );
        http.formLogin(login -> login.loginPage("/login")
                .loginProcessingUrl("/authenticateUser")
                .defaultSuccessUrl("/home",true)
                .permitAll())
                .logout(LogoutConfigurer::permitAll);
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
