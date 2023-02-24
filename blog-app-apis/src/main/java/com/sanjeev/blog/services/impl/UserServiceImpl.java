package com.sanjeev.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanjeev.blog.config.AppConstants;
import com.sanjeev.blog.enteties.Role;
import com.sanjeev.blog.enteties.User;
import com.sanjeev.blog.exceptions.ResourceNotFoundException;
import com.sanjeev.blog.payloads.UserDto;
import com.sanjeev.blog.repositories.RoleRepo;
import com.sanjeev.blog.repositories.UserRepo;
import com.sanjeev.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		this.userRepo.save(user);
		return this.userToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id", userId));
		this.copy(user, userDto);
		User updatedUser = this.userRepo.save(user);
		UserDto userToDto = this.userToDto(updatedUser);
		return userToDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> all = this.userRepo.findAll();
		List<UserDto> dtoUsers=all.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return dtoUsers;
	}

	@Override
	public void deleteUser(Integer UserId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(UserId).orElseThrow(()->new ResourceNotFoundException("User", "id", UserId));
		userRepo.delete(user);

	}
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}
	/**
	 * copies data from right argument to left argument.
	 * @param user
	 * @param userDto
	 */
	private void copy(User user, UserDto userDto) {
		// TODO Auto-generated method stub
		//don't try to copy id because id is not passed in body
		//user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());	
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//password encoded
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//set roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}