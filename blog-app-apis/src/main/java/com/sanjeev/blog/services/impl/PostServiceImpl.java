package com.sanjeev.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sanjeev.blog.enteties.Category;
import com.sanjeev.blog.enteties.Post;
import com.sanjeev.blog.enteties.User;
import com.sanjeev.blog.exceptions.ResourceNotFoundException;
import com.sanjeev.blog.payloads.PostDto;
import com.sanjeev.blog.payloads.PostResponse;
import com.sanjeev.blog.repositories.CategoryRepo;
import com.sanjeev.blog.repositories.PostRepo;
import com.sanjeev.blog.repositories.UserRepo;
import com.sanjeev.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("Default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(Integer postId, PostDto postDto) {
		// TODO Auto-generated method stub
		this.modelMapper.getConfiguration().setSkipNullEnabled(true);
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		this.modelMapper.map(postDto, post);
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
		// TODO Auto-generated method stub
		Sort sort=Sort.by(sortBy);
		if(sortOrder.equalsIgnoreCase("dsc")) {
			sort=sort.descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> all = pagePost.getContent();
		List<PostDto> list = all.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
		// TODO Auto-generated method stub
		Sort sort=Sort.by(sortBy);
		if(sortOrder.equalsIgnoreCase("dsc")) {
			sort=sort.descending();
		}
		PageRequest p = PageRequest.of(pageNumber, pageSize,sort);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		Page<Post> pagePost = this.postRepo.findByCategory(category,p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> list = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
		// TODO Auto-generated method stub
		Sort sort=Sort.by(sortBy);
		if(sortOrder.equalsIgnoreCase("dsc")) {
			sort=sort.descending();
		}
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		PageRequest p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findByUser(user,p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> list = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());	
		return postResponse;
	}

	@Override
	public PostResponse searchPostByTitle(String keyword,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
		// TODO Auto-generated method stub
		Sort sort=Sort.by(sortBy);
		if(sortOrder.equalsIgnoreCase("dsc")) {
			sort=sort.descending();
		}
		PageRequest p= PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findByTitleContaining(keyword,p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> list = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());	
		return postResponse;
	}
	

}
