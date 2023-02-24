package com.sanjeev.blog.services;

import com.sanjeev.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
 