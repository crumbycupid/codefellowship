package com.codefellowship.Security.controllers;

import com.codefellowship.Security.models.ApplicationUser;
import com.codefellowship.Security.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

        autoAuthWithHttpServletRequest(username, password);
        return new RedirectView("/login");
    }

    public void autoAuthWithHttpServletRequest(String username, String password){
        try {
            request.login(username, password);
        }catch (ServletException se){
            se.printStackTrace();
        }
    }

    @GetMapping("/login")
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
            m.addAttribute("LastName", dbUser.getLastName());
        } try {

        }catch (RuntimeException runtimeException){
            throw new RuntimeException("Something went wrong");
        }
        return "index.html";
    }

    @GetMapping("/secret")
    public String getSecret(){
        return "secret.html";
    }

    @GetMapping("/user/{id}")
    public String getOneApplicationUser(@PathVariable Long id, Model m, Principal p){
        ApplicationUser authenticatedUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("authUser", authenticatedUser);
        ApplicationUser viewUser = applicationUserRepository.findById(id).orElseThrow();
        m.addAttribute("viewUser", viewUser);
        return "user-info.html";
    }

    @PutMapping("/user/{id}")
    public RedirectView editApplicationUserInfo(@PathVariable Long id, String username, String firstName, Principal p, RedirectAttributes redir) throws ServletException{
        ApplicationUser userToBeEdited = applicationUserRepository.findById(id).orElseThrow();
        if(p.getName().equals(userToBeEdited.getUsername())){
        userToBeEdited.setUsername(username);
        userToBeEdited.setFirstName(firstName);
        applicationUserRepository.save(userToBeEdited);
        request.logout();
        autoAuthWithHttpServletRequest(username, userToBeEdited.getPassword());
        }else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another users info");
        }
        return new RedirectView("/user/" + id);
    }
}
