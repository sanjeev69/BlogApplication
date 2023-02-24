package com.sanjeev.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjeev.blog.payloads.ApiResponse;
import com.sanjeev.blog.payloads.CategoryDto;
import com.sanjeev.blog.services.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto newCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(newCategoryDto,HttpStatus.CREATED);
	}
	//update category
	@PutMapping("/{cid}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable("cid") Integer categoryId,@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryId,categoryDto);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
		
	}
	//get category
	@GetMapping("/{cid}")
	public ResponseEntity<CategoryDto> geCategory(@PathVariable("cid") Integer categoryId) {
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.FOUND);
	}
	//get all category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> allCategories = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(allCategories,HttpStatus.OK);
	}
	//delete category
	@DeleteMapping("/{uid}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("uid") Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category "+ categoryId +" deleted Successfully",true),HttpStatus.OK);
	}
	
}
