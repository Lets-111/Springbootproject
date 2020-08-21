package com.MyCompany.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.MyCompany.rest.entities.User;
import com.MyCompany.rest.exceptions.UserExistsException;
import com.MyCompany.rest.exceptions.UserNotFoundException;
import com.MyCompany.rest.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	
	// Autowire the userService.
	@Autowired
	private UserService userService;
	
	// method to getAll Users
	
	@GetMapping("/users")
	public List<User> getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	// Method to add a createUser
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user,UriComponentsBuilder builder)
	{
		try
		{
		userService.createUser(user);
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
		}
		catch(UserExistsException es)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,es.getMessage());
		}
	}
	
	
	// Method to get a user by id.
	
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id")  Long id)
	{
		try
		{
		return userService.getUserById(id);
		}
		catch(UserNotFoundException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	
	// Method to update user by ID
	
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user)
	{
		try {
		return userService.updateUserById(id,user);
		}
		catch(UserNotFoundException ex)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
		}
	}
	
	
	// Delete a user by ID
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id)
	{
		 userService.deleteUserById(id);
	}
	
	// get user by username
	
	@GetMapping("/users/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username)
	{
		return userService.getUserByUsername(username);
	}
	
}
