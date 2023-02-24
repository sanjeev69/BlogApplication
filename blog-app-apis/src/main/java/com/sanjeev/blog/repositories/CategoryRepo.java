package com.sanjeev.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjeev.blog.enteties.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
