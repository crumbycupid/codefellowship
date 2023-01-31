package com.codefellowship.Security.controllers;

import com.codefellowship.Security.models.ApplicationUser;
import com.codefellowship.Security.repositories.ApplicationUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    HttpServletRequest request;


    @PostMapping("/signup")
    public RedirectView createApplicationUser(String username, String lastName, String password, String firstName, String bio){
        String hashedPW = passwordEncoder.encode(password);
        ApplicationUser newUser = new ApplicationUser(username, lastName, hashedPW, firstName, new Date(), bio);
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }

    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){ return "signup.html";}

    @GetMapping("/")
    public String getHome(Model m, Principal p){
        if (p != null){
            String username = p.getName();
            ApplicationUser dbUser = applicationUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("FirstName", dbUser.getFirstName());
        }
        return "index.html";
    }

    @GetMapping("/secret")
    public String getSecret(){
        return "secret.html";
    }
}
