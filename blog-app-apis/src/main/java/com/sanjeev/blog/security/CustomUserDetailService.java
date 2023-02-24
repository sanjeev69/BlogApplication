package com.sanjeev.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanjeev.blog.enteties.User;
import com.sanjeev.blog.exceptions.ResourceNotFoundException;
import com.sanjeev.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database from database
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email", username));
		
		return user;
	}

}
