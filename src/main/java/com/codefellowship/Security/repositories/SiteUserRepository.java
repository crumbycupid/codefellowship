package com.codefellowship.Security.repositories;

import com.codefellowship.Security.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

//Create Site User Repo
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    public SiteUser findByUsername(String username);
}
