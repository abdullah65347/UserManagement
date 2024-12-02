package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.exception.UserNotFoundException;
import com.main.model.User;
import com.main.repository.UserRepository;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin // solve Cros error
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/user")
	public User newUser(@RequestBody User newUser) {
		
		return userRepository.save(newUser);
	}
	
	@GetMapping("/users")
	public List<User>  getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id){
		return userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException(id));
	}
	
	@PutMapping("user/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User newUser ){
	
		return userRepository.findById(id)
				.map(user ->{
					user.setUsername(newUser.getUsername());
					user.setName(newUser.getName());
					user.setEmail(newUser.getEmail());
					return userRepository.save(user);
				}).orElseThrow(()-> new UserNotFoundException(id));
	}
	
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable Long id) {
		if(!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}else {
			userRepository.deleteById(id);
		}
		return "User with id "+id+" has been deleted successfully.";
		
	}
	

}