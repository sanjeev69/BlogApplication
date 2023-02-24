package com.sanjeev.blog.services;

import com.sanjeev.blog.payloads.PostDto;
import com.sanjeev.blog.payloads.PostResponse;

public interface PostService {
	
	//create Post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update Post
	PostDto updatePost(Integer postId,PostDto postDto);
	//delete post
	void deletePost(Integer postId);
	//get All Posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
	//getPostById
	PostDto getPostById(Integer postId);
	//getPostByCategory
	PostResponse getPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
	//getPostByUser
	PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
	//search
	PostResponse searchPostByTitle(String keyword,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
	
}
