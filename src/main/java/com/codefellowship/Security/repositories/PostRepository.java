package com.codefellowship.Security.repositories;

import com.codefellowship.Security.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
