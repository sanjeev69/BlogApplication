package com.sanjeev.blog.services;

import java.util.List;
import com.sanjeev.blog.payloads.CategoryDto;

public interface CategoryService {
	//create 
	CategoryDto createCategory(CategoryDto categoryDto);
	//update 
	CategoryDto updateCategory(Integer categoryId,CategoryDto categoryDto);
	//delete
	void deleteCategory(Integer categoryId);
	//get
	CategoryDto getCategory(Integer categoryId);
	//get all
	List<CategoryDto> getAllCategories();
	 
}
