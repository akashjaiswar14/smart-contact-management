package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    // user create and login using java code with in memory service
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1 = User
    //                         .withUsername("admin123")
    //                         .password("admin123")
    //                         .roles("ADMIN")
    //                         .build();
    //     var InMemoryUserDetailsManager =  new InMemoryUserDetailsManager(); 
    //     return InMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    // configuratin of authentication provider spring security
    @SuppressWarnings("deprecation")
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // userdetail service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        // configuration 
        // url config kia h ki kon kon page public accessble h 
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll(); 
        });

        // form default login
        // agar hume kch bhi chnage krna h toh hum yah ayenge : form login se related
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/profile", true);  
            // .failureForwardUrl("/login?error=true");

            formLogin.usernameParameter("email")
            .passwordParameter("password");

            /*
            formLogin.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
                }
                
            });

            formLogin.successHandler(new AuthenticationSuccessHandler() {

                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
                }
                
            });*/
            formLogin.failureHandler(authFailureHandler);
        });


        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        httpSecurity.logout(logoutForm ->{
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
