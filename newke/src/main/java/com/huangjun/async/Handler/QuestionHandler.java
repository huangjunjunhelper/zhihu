package com.huangjun.async.Handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.async.EventHandler;
import com.huangjun.async.EventModel;
import com.huangjun.eunm.EventType;
import com.huangjun.model.Feed;
import com.huangjun.model.Question;
import com.huangjun.model.User;
import com.huangjun.service.FeedService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;

@Service
public class QuestionHandler implements EventHandler{
	@Autowired
	private UserService userSerivce;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private FeedService feedService;
	@Override
	public void handle(EventModel event) {
		Feed feed = new Feed();
		feed.setCreated_date(new Date());
		feed.setEntity_type(event.getEntityType());
		User user = userSerivce.findById(event.getOwnerId());
		Question question = questionService.findById(event.getEntityId());
		feed.setUser_id(user.getId());
		JSONObject json = new JSONObject();
		json.put("name",user.getName());
		json.put("url", user.getHead_url());
		json.put("id", user.getId());
		json.put("question_id", event.getEntityId());
		json.put("question_title", question.getTitle());
		feed.setData(json.toJSONString());
		feedService.addFeed(feed);
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.QUESTION);
	}

}
