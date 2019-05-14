package com.huangjun.async.Handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.async.EventHandler;
import com.huangjun.async.EventModel;
import com.huangjun.eunm.EventType;
import com.huangjun.service.EmailService;

@Service
public class EmailHandler implements EventHandler{
	@Autowired
	private EmailService emailService; 
	
	@Override
	public void handle(EventModel event) {
		Map<String,String> map = new HashMap<>();
		map.put("username", event.getMap().get("username"));
		emailService.sendTemplateMail("huangjunjun1997@163.com", "登陆异常", map);
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.LOGIN);
	}
	
}
