package com.in28minutes.rest.webservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.rest.webservices.entity.Post;
import com.in28minutes.rest.webservices.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
