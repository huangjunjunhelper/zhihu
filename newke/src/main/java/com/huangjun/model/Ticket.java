package com.huangjun.model;

import java.util.Date;

import lombok.Data;

@Data
public class Ticket {
	private int id;
	private int user_id;
	private String ticket;
	private Date expired;
	private int status;
}
