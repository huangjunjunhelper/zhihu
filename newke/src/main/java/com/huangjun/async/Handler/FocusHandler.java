package com.huangjun.async.Handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.async.EventHandler;
import com.huangjun.async.EventModel;
import com.huangjun.eunm.EntityType;
import com.huangjun.eunm.EventType;
import com.huangjun.model.Feed;
import com.huangjun.model.Message;
import com.huangjun.model.Question;
import com.huangjun.model.User;
import com.huangjun.service.FeedService;
import com.huangjun.service.MessageService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;
import com.huangjun.util.WendaUtil;

@Service
public class FocusHandler implements EventHandler {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userSerivce;
	@Autowired
	private FeedService feedService;
	@Autowired
	private QuestionService questionService;
	@Override
	public void handle(EventModel event) {
		Feed feed = new Feed();
		feed.setCreated_date(new Date());
		feed.setEntity_type(event.getEntityType());
		feed.setUser_id(event.getOwnerId());
		JSONObject json = new JSONObject();
		User user = userSerivce.findById(event.getOwnerId());
		json.put("name", user.getName());
		json.put("url", user.getHead_url());
		Message message = new Message();
		message.setFrom_id(WendaUtil.SYSTEM_USER_ID);
		message.setTo_id(event.getToId());
		message.setConversation_id();
		message.setCreated_date(new Date());
		message.setHas_read(0);
		if(EntityType.USER.equals(event.getEntityType())) {
			message.setContent("有人关注了你");
			User focus = userSerivce.findById(event.getEntityId());
			json.put("userName", focus.getName());
			json.put("userId", focus.getId());
			feed.setData(json.toJSONString());
			feedService.addFeed(feed);
			messageService.addMessage(message);
		}
		else if(EntityType.QUESTION.equals(event.getEntityType())) {
			message.setContent("有人关注了你的问题");
			Question question = questionService.findById(event.getEntityId());
			json.put("title", question.getTitle());
			json.put("questionId",question.getId());
			feed.setData(json.toJSONString());
			feedService.addFeed(feed);
			messageService.addMessage(message);
		}	
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.FOCUS);
	}

}
