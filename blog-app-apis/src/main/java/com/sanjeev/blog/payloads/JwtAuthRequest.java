package com.sanjeev.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JwtAuthRequest {
	@NotNull(message = "Email can not be null")
	@NotEmpty(message = "Email can not be empty")
	private String username;
	
	@NotNull(message = "Password can not be null")
	@NotEmpty(message = "Password can not be empty")
	private String password;
}
