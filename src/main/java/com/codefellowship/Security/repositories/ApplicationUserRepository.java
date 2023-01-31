package com.codefellowship.Security.repositories;

import com.codefellowship.Security.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

//Create Site User Repo
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    public ApplicationUser findByUsername(String username);
}
