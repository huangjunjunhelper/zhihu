package com.huangjun.model;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.eunm.EntityType;

public class Feed {
	private int id;
	private int user_id;
	private EntityType entity_type;
	private Date created_date;
	private String data;
	private JSONObject dataJSON = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public EntityType getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(EntityType entity_type) {
		this.entity_type = entity_type;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
		dataJSON = JSONObject.parseObject(data);
	}
	
	public JSONObject getDataJSON() {
		return dataJSON;
	}

	
	


	
	
}
