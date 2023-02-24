package com.sanjeev.blog.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjeev.blog.enteties.Category;
import com.sanjeev.blog.enteties.Post;
import com.sanjeev.blog.enteties.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
	public Page<Post> findByUser(User user,PageRequest pageRequest);
	public Page<Post> findByCategory(Category category, PageRequest pageRequest);
	public Page<Post> findByTitleContaining(String title, PageRequest pageRequest);
}
