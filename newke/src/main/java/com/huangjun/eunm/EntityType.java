package com.huangjun.eunm;

public enum EntityType {
	QUESTION(0),COMMENT(1),USER(2);
	private int value;
	private EntityType(int value) {
		this.value = value;
	}
	
}
