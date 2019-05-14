package com.huangjun.model;

import java.util.Date;

import lombok.Data;

@Data
public class Question {
	private int id;
	private String title;
	private String content;
	private int user_id;
	private Date created_date;
	private int comment_count;
	
}
