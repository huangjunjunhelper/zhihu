package com.huangjun.model;

import lombok.Data;


@Data
public class User {
	private int id;
	private String name;
	private String password;
	private String salt;
	private String head_url;
	
}

