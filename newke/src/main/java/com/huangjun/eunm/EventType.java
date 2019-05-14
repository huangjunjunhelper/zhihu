package com.huangjun.eunm;

public enum EventType {
	LOGIN(0),COMMENT(1),LIKE(2),QUESTION(3),FOCUS(4),REGISTER(5);
	private int value;
	private EventType(int value) {
		this.value = value;
	}
}
