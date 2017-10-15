package org.zhubao.entity;

import lombok.Data;

@Data
public class User {

	private long id;
	
	private String username;
	
	private String email;
	
	private int age;
}
