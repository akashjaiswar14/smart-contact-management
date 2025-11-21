package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    // user create and login using java code with in memory service
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User
                            .withUsername("admin123")
                            .password("admin123")
                            .roles("ADMIN")
                            .build();
        var InMemoryUserDetailsManager =  new InMemoryUserDetailsManager(); 
        return InMemoryUserDetailsManager;
    }
}
