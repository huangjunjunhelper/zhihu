package com.huangjun.model;

import java.util.Date;

import com.huangjun.eunm.EntityType;

import lombok.Data;

@Data
public class Comment {
	private int id;
	private String content;
	private int user_id;
	private Date created_date;
	private int entity_id;
	private String entity_type;
	private int status;

	
}
