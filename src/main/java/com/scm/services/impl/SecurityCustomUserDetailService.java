package com.scm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repositories.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //     // apne use ki load krana h
    //     return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found with this email id "+username));
    // }

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var user = userRepo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

    System.out.println("== LOADED USER ==");
    System.out.println("Email: " + user.getEmail());
    System.out.println("Password: " + user.getPassword());
    System.out.println("Enabled: " + user.isEnabled());
    System.out.println("Roles: " + user.getRoleList());

    return user;
}


}
