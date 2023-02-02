package com.codefellowship.Security.controllers;


import com.codefellowship.Security.models.ApplicationUser;
import com.codefellowship.Security.models.Post;
import com.codefellowship.Security.repositories.ApplicationUserRepository;
import com.codefellowship.Security.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/feed")
    public RedirectView addNewPost(Principal p, Model m, String body, String subject){
        if (p !=null){
            String username = p.getName();
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(username);
            m.addAttribute("applicationUser", applicationUser);
            Post post = new Post(body, subject);
            post.setApplicationUser(applicationUser);
            postRepository.save(post);
        }
        return new RedirectView("/user-info");
    }

}
