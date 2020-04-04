package com.in28minutes.rest.webservices.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.entity.Post;
import com.in28minutes.rest.webservices.entity.User;
import com.in28minutes.rest.webservices.exceptionutility.UserNotFoundException;
import com.in28minutes.rest.webservices.repository.PostRepository;
import com.in28minutes.rest.webservices.repository.UserRepository;
import com.in28minutes.rest.webservices.service.UserDaoService;

@RestController
public class UserJpaController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	// retrieve all users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	// retrieve one user
	@GetMapping(path = "/jpa/users/{id}")
	public Optional<User> retrieveUser(@PathVariable("id") int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("id - " + id);
		return user;
	}

	// delete one user
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		userRepository.deleteById(id);

	}

	// create user and return location of the user in header
	@PostMapping(path = "/jpa/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	// retrieve Post of users
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveUsersPost(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("id - " + id);
		List<Post> post = user.get().getPosts();
		return post;
	}

	// We want to post for a specific user
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent())
			throw new UserNotFoundException("id - " + id);
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
