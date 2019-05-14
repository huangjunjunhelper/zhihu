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
import com.huangjun.service.CommentService;
import com.huangjun.service.FeedService;
import com.huangjun.service.FocusService;
import com.huangjun.service.MessageService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;
import com.huangjun.util.WendaUtil;

@Service
public class CommentHandler implements EventHandler{
	@Autowired
	private MessageService messageService;

	@Override
	public void handle(EventModel event) {
		
//		JSONObject json = new JSONObject();
//		Feed feed = new Feed();
//		feed.setCreated_date(new Date());
//		feed.setUser_id(event.getOwnerId());
//		feed.setEntity_type(event.getEntityType().toString().toLowerCase());
//		User user = userService.findById(event.getOwnerId());
//		json.put("name", user.getName());
//		json.put("headUrl",user.getHead_url());
//		if(event.getEntityType().equals(EntityType.QUESTION)) {
//			if(event.getType().equals(EventType.QUESTION))
//				json.put("description", "提出了该问题:");
//			else if(event.getType().equals(EventType.FOCUS))
//				json.put("description", "关注了该问题:");
//			else if(event.getType().equals(EventType.LIKE))
//				json.put("description", "点赞了该问题:");
//			else
//				return;
//			Question question = questionService.findById(event.getEntityId());
//			json.put("title", question.getTitle());
//			json.put("id",question.getId());
//		}
//		else if(event.getEntityType().equals(EntityType.USER)) {
//			User u = userService.findById(event.getEntityId());
//			json.put("name",u.getName());
//			json.put("id",u.getId());
//		}
//		feed.setData(json.toJSONString());
//		feedService.addFeed(feed);
		
//		JSONObject json = new JSONObject();
//		json.put("created_date", new Date());
//		json.put("user_id", event.getOwnerId());
//		json.put("entity_type", event.getEntityType().toString().toLowerCase());
//		User user = userService.findById(event.getOwnerId());
//		json.put("name", user.getName());
//		json.put("headUrl",user.getHead_url());
//		if(event.getEntityType().equals(EntityType.QUESTION)) {
//			if(event.getType().equals(EventType.QUESTION))
//				json.put("description", "提出了该问题:");
//			else if(event.getType().equals(EventType.FOCUS))
//				json.put("description", "关注了该问题:");
//			else if(event.getType().equals(EventType.LIKE))
//				json.put("description", "点赞了该问题:");
//			else
//				return;
//			Question question = questionService.findById(event.getEntityId());
//			json.put("title", question.getTitle());
//			json.put("id",question.getId());
//		}
//		else 
//			return ;
//		List<Integer> list = focusService.getFans(EntityType.USER, event.getOwnerId(), 0, Integer.MAX_VALUE);
//		for(Integer id:list) {
//			feedService.addFeed(id,json.toJSONString());
//		}
		Message message = new Message();
		message.setFrom_id(WendaUtil.SYSTEM_USER_ID);
		message.setTo_id(event.getToId());
		message.setConversation_id();
		message.setCreated_date(new Date());
		message.setHas_read(0);
		if(EntityType.QUESTION.equals(event.getEntityType())) {
			message.setContent("你的问题收到了一条评论");
			messageService.addMessage(message);
		}
		
		
		
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.COMMENT);
	}
	
}
