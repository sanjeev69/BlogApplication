package com.sanjeev.blog.enteties;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
	@Id
	private Integer id;
	
	private String name;
}
