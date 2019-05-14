package com.huangjun.model;

import java.util.Date;

import lombok.Data;

@Data
public class Message {
	private int id;
	private int from_id;
	private int to_id;
	private String content;
	private String conversation_id;
	private Date created_date;
	private int has_read;

	public void setConversation_id() {
		this.conversation_id = from_id > to_id ? to_id+"-"+from_id: from_id+"-"+to_id;			
	}
}
