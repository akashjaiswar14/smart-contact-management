package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

    if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        OAuth2AuthenticatedPrincipal principal =
                (OAuth2AuthenticatedPrincipal) oauthToken.getPrincipal();

        var oauth2User = (OAuth2User)authentication.getPrincipal();
        String userName = "";

        if (registrationId.equalsIgnoreCase("google")) {
            System.out.println("email getting from google");
            userName = oauth2User.getAttribute("email").toString();
        }
        else if (registrationId.equalsIgnoreCase("github")) {
            System.out.println("email getting from github");
            userName = oauth2User.getAttribute("email") != null ?
                                oauth2User.getAttribute("email").toString(): 
                                oauth2User.getAttribute("login").toString()+"@gmail.com";
        }
        return userName; 
    }

    // Normal login
    return authentication.getName();
}

}
