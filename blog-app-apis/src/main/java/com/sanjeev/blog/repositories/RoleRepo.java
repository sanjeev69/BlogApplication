package com.sanjeev.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjeev.blog.enteties.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	
}
