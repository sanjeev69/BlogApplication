package com.sanjeev.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get token
		String requestToken= request.getHeader("Authorization");
		//token format Bearer 23423n34h5454u5
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username=this.jwtTokenHelper.getUsernameFromToken(token);
			}catch (IllegalArgumentException e) {
				System.out.println("unable to get JWT token");
			}catch (ExpiredJwtException e) {
				System.out.println("Token has been expired");
			}catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
		}else {
			System.out.println("JWT token does not starts with Bearer");
		}
		//once we get token ,now validate token 
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token,userDetails)) {
				//everything is fine lets authenticate
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
					UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);;
				
			}else {
				System.out.println("Invalid JWT token");
			}
		}else {
			System.out.println("Username is null or context is not null");
		}
		filterChain.doFilter(request, response); 
		
	}

}
