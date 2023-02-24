 package com.sanjeev.blog.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjeev.blog.enteties.User;
import com.sanjeev.blog.exceptions.WrongPasswordException;
import com.sanjeev.blog.payloads.JwtAuthRequest;
import com.sanjeev.blog.payloads.JwtAuthResponse;
import com.sanjeev.blog.payloads.UserDto;
import com.sanjeev.blog.security.JwtTokenHelper;
import com.sanjeev.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	 
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody @Valid JwtAuthRequest request
			) throws BadCredentialsException{
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User)userDetails,UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
	private void authenticate(String username,String password) throws WrongPasswordException {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try{
			this.authenticationManager.authenticate(authenticationToken);
		}catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new WrongPasswordException("Invalid Usename or Password !!"); 
		} 
	}
	//register new user Api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
}
