package com.huangjun.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.async.EventModel;
import com.huangjun.async.EventProducer;
import com.huangjun.eunm.EntityType;
import com.huangjun.eunm.EventType;
import com.huangjun.model.Comment;
import com.huangjun.model.HostUser;
import com.huangjun.model.Question;
import com.huangjun.model.User;
import com.huangjun.service.CommentService;
import com.huangjun.service.LikeService;
import com.huangjun.service.QuestionService;

@Controller
public class LikeController {
	@Autowired
	private LikeService likeService;
	@Autowired
	private HostUser hostUser;
	@Autowired
	private EventProducer eventProducer;
	@Autowired
	private CommentService commentService;
	@Autowired
	private QuestionService questionService;

	
	@RequestMapping(path= {"/likeComment"},method= {RequestMethod.POST})
	@ResponseBody
	public String likeComment(@RequestBody Map<String,String> json) {
		User localUser = hostUser.getUser();
		int entityId = Integer.parseInt(json.get("entityId"));
		long count = likeService.like(localUser.getId(), EntityType.COMMENT,entityId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("likeCount", count);
		Comment comment = commentService.findComment(entityId);
		Question question = questionService.findById(comment.getEntity_id());
		eventProducer.executeEvent(new EventModel().setType(EventType.LIKE)
									.setEntityType(EntityType.COMMENT).setEntityId(entityId)
									.setOwnerId(localUser.getId())
									.setToId(comment.getUser_id()));
		return jsonObject.toJSONString();
	} 
	
	@RequestMapping(path= {"/dislikeComment"},method= {RequestMethod.POST})
	@ResponseBody
	public String dislikeComment(@RequestBody Map<String,String> json) {
		long count = likeService.dislike(hostUser.getUser().getId(), EntityType.COMMENT, Integer.parseInt(json.get("entityId")));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("likeCount", count);
		return jsonObject.toJSONString();
	}
	
	@RequestMapping(path= {"/likeQuestion"},method= {RequestMethod.POST})
	@ResponseBody
	public String likeQuestion(@RequestBody Map<String,String> json) {
		User localUser = hostUser.getUser();
		int entityId = Integer.parseInt(json.get("entityId"));
		long count = likeService.like(localUser.getId(), EntityType.QUESTION, entityId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("likeCount", count);
		Question question = questionService.findById(entityId);
		eventProducer.executeEvent(new EventModel().setType(EventType.LIKE)
				.setEntityType(EntityType.QUESTION).setEntityId(entityId)
				.setOwnerId(localUser.getId())
				.setToId(question.getUser_id()));
		return jsonObject.toJSONString();
	} 
	@RequestMapping(path= {"/dislikeQuestion"},method= {RequestMethod.POST})
	@ResponseBody
	public String dislikeQuestion(@RequestBody Map<String,String> json) {
		long count = likeService.dislike(hostUser.getUser().getId(), EntityType.QUESTION, Integer.parseInt(json.get("entityId")));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("likeCount", count);
		return jsonObject.toJSONString();
	}
}
