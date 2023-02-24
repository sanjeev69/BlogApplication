package com.sanjeev.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjeev.blog.enteties.Category;
import com.sanjeev.blog.exceptions.ResourceNotFoundException;
import com.sanjeev.blog.payloads.CategoryDto;
import com.sanjeev.blog.repositories.CategoryRepo;
import com.sanjeev.blog.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category=this.dtoToCategory(categoryDto);
		Category addedCategory=this.categoryRepo.save(category); 
		return this.categoryToDto(addedCategory);
	}

	@Override
	public CategoryDto updateCategory(Integer categoryId,CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		this.copy(category, categoryDto);
		Category save = this.categoryRepo.save(category);
		
		return this.categoryToDto(save);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catergory","id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catergory","id", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> all = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos=all.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}
	private Category dtoToCategory(CategoryDto categoryDto) {
		 return this.modelMapper.map(categoryDto,Category.class);
	}
	
	private CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category,CategoryDto.class);
	}
	
	private void copy(Category category,CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		//don't try to copy id because id is not passed in body
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
	}

}
