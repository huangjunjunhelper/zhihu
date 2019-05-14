package com.huangjun.async;

import java.util.List;

import com.huangjun.eunm.EventType;

public interface EventHandler {
	void handle(EventModel event);
	List<EventType> getSupportEventType();
}
