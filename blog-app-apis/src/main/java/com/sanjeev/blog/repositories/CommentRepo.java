package com.sanjeev.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjeev.blog.enteties.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
