package com.huangjun.async;

import java.util.HashMap;
import java.util.Map;

import com.huangjun.eunm.EntityType;
import com.huangjun.eunm.EventType;

public class EventModel {
	private EventType type;
	private int ownerId;
	private EntityType entityType;
	private int entityId;
	private int toId;
	private Map<String,String> map = new HashMap<>();
	
	
	
	public EventModel() {
		super();
	}
	
	
	public Map<String,String> getMap(){
		return map;
	}
	
	public String getEx(String key) {
		return map.get(key);
	}
	
	public EventModel setEx(String key,String value) {
		map.put(key, value);
		return this;
	}
	
	public EventType getType() {
		return type;
	}
	public EventModel setType(EventType type) {
		this.type = type;
		return this;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public EventModel setOwnerId(int ownerId) {
		this.ownerId = ownerId;
		return this;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	public EventModel setEntityType(EntityType entityType) {
		this.entityType = entityType;
		return this;
	}
	public int getEntityId() {
		return entityId;
	}
	public EventModel setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}
	public int getToId() {
		return toId;
	}
	public EventModel setToId(int toId) {
		this.toId = toId;
		return this;
	}
	
	
}
