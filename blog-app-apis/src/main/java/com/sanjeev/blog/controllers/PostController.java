package com.sanjeev.blog.controllers;

 

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanjeev.blog.config.AppConstants;
import com.sanjeev.blog.payloads.ApiResponse;
import com.sanjeev.blog.payloads.PostDto;
import com.sanjeev.blog.payloads.PostResponse;
import com.sanjeev.blog.services.FileService;
import com.sanjeev.blog.services.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	//create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	//update post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId,@RequestBody PostDto postDto){
		PostDto updatedPost = this.postService.updatePost(postId, postDto);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	//get all posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getAllPostsByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT,required = false) String sortBy,
			@RequestParam(value = "sortOrder",defaultValue = AppConstants.DEFAULT_SORT_ORDER,required = false) String sortOrder){
		PostResponse postByCategory = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<PostResponse>(postByCategory,HttpStatus.OK);
	}
	//get all posts of user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable Integer userId,
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT,required = false) String sortBy,
			@RequestParam(value = "sortOrder",defaultValue = AppConstants.DEFAULT_SORT_ORDER,required = false) String sortOrder){
		PostResponse postByUser = this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<PostResponse>(postByUser,HttpStatus.OK);
	}
	
	// get all posts 
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT,required = false) String sortBy,
			@RequestParam(value = "sortOrder",defaultValue = AppConstants.DEFAULT_SORT_ORDER,required = false) String sortOrder){
		
		PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	//get post by its id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	//delete post by id 
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		ApiResponse response = new ApiResponse("post deleted successfully with id: "+postId,true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	//search post by keyword
	@GetMapping("/search/posts")
	public ResponseEntity<PostResponse> searchPostByTitle(
			@RequestParam(name = "keyword",required = true) String keyword,
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT,required = false) String sortBy,
			@RequestParam(value = "sortOrder",defaultValue = AppConstants.DEFAULT_SORT_ORDER,required = false) String sortOrder){
		PostResponse searchedPosts = this.postService.searchPostByTitle(keyword,pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<PostResponse>(searchedPosts,HttpStatus.OK);
	}
	
	//upload image controller
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@PathVariable Integer postId,
			@RequestParam("image") MultipartFile image) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String uploadedImageName = this.fileService.uploadImage(this.path, image);
		postDto.setImageName(uploadedImageName);
		PostDto updatedPost = this.postService.updatePost(postId, postDto);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		InputStream inputStream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}
	
}
