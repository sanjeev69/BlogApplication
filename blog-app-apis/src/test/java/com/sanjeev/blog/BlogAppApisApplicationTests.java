package com.sanjeev.blog;

import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sanjeev.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private UserRepo userRepo;
	@Test
	public void classTest() {
		int totalPages=(int) Math.ceil(88f/20);
		System.out.println(totalPages);
	}

}
