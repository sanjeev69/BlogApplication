package com.sanjeev.blog.payloads;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty(message = "Name can't be empty")
	@NotNull(message = "Name can't be empty")
	@Size(min = 4,message = "name should be atleast of 4 characters")
	private String name;
	@NotEmpty(message = "email can not be empty")
	@Email(message = "invlid email")
	private String email;
	
	@NotEmpty(message = "password can't be empty")
	@NotNull(message = "password can't be empty")
	private String password;
	
	@NotNull(message = "about can't be null")
	@NotEmpty(message = "about can't be empty")
	private String about;
	
	private List<RoleDto> roles=new ArrayList<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
