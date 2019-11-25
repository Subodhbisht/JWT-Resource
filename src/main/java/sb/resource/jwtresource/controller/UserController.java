package sb.resource.jwtresource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.model.UserSearchCriteria;
import sb.resource.jwtresource.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody User user){
		User persistedUser;
		persistedUser = userService.save(user);
		return new ResponseEntity<String>("User with id ="+persistedUser.getUserId()+" is created.",HttpStatus.OK);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody User user){
		userService.update(user);
		return new ResponseEntity<String>("User with id ="+user.getUserId()+" is updated.",HttpStatus.OK);
	}
	
	@GetMapping("/getUsers")
	public List<User> getUsers(@RequestBody UserSearchCriteria criteria){
		return userService.findUsers(criteria);
	}
	
	@GetMapping("/user/{userName}")
	public User getUser(@PathVariable String userName) {
		return userService.getUser(userName);
	}
}
