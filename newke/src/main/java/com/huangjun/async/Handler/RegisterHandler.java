package com.huangjun.async.Handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huangjun.async.EventHandler;
import com.huangjun.async.EventModel;
import com.huangjun.eunm.EventType;

@Service
public class RegisterHandler implements EventHandler{

	@Override
	public void handle(EventModel event) {
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.REGISTER);
	}

}
