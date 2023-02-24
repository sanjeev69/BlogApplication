package com.sanjeev.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjeev.blog.payloads.ApiResponse;
import com.sanjeev.blog.payloads.CommentDto;
import com.sanjeev.blog.services.CommentService;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId){
		CommentDto createdCommentdto = this.commentService.createComment(commentDto,  postId);
		return new ResponseEntity<CommentDto>(createdCommentdto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		String message="comment deleted successfully with id : "+commentId;
		return new ResponseEntity<ApiResponse>(new ApiResponse(message,true),HttpStatus.OK);
	}

}
