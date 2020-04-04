package com.in28minutes.rest.webservices.controller;

import java.net.URI;
import java.util.List;

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

import com.in28minutes.rest.webservices.entity.User;
import com.in28minutes.rest.webservices.exceptionutility.UserNotFoundException;
import com.in28minutes.rest.webservices.service.UserDaoService;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userDaoService;

	// retrieve all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}

	// retrieve one user
	@GetMapping(path = "/users/{id}")
	public User retrieveUser(@PathVariable("id") int id) {
		User user = userDaoService.findOne(id);
		if (user == null)
			throw new UserNotFoundException("id - " + id);
		
		//HATEOAS :- Send some exatra things along with response which may required for client
		//ex:- while sending user details we can send url to get all the users
		//Read Somewhere, code is not working now
		/*RepresentationModel<User> representationModel = new RepresentationModel();
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		representationModel.add(linkTo.withRel("all-user"));*/
		return user;
	}

	// create user and return location of the user in header
	@PostMapping(path = "/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	// Delete one user
	@DeleteMapping(path = "/users/{id}")
	public void deleteByUserId(@PathVariable("id") int id) {
		User user = userDaoService.deleteUserById(id);
		if (user == null)
			throw new UserNotFoundException("id - " + id);

	}

}
