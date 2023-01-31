package com.codefellowship.Security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationUserController {

    @GetMapping("/")
    public String getHome(){
        return "index.html";
    }

    @GetMapping("/secret")
    public String getSecret(){
        return "secret.html";
    }
}
