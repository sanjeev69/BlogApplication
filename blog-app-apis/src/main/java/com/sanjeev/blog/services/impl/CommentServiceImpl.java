package com.sanjeev.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjeev.blog.enteties.Comment;
import com.sanjeev.blog.enteties.Post;
import com.sanjeev.blog.exceptions.ResourceNotFoundException;
import com.sanjeev.blog.payloads.CommentDto;
import com.sanjeev.blog.repositories.CommentRepo;
import com.sanjeev.blog.repositories.PostRepo;
import com.sanjeev.blog.services.CommentService;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override	
	public CommentDto createComment(CommentDto commentDto,Integer postId) {
		//User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setDate(new Date());
		Comment save = this.commentRepo.save(comment);
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
		this.commentRepo.delete(comment);
	}

}
