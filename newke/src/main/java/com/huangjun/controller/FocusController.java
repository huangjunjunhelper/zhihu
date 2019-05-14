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
import com.huangjun.model.HostUser;
import com.huangjun.model.Question;
import com.huangjun.service.FocusService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;

@Controller
public class FocusController {
	@Autowired
	private FocusService focusService;
	@Autowired
	private HostUser hostUser;
	@Autowired
	private EventProducer eventProducer;
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(path= {"/focusUser"},method= {RequestMethod.POST})
	@ResponseBody
	public String focusUser(@RequestBody Map<String,String> json) {
		int userId = hostUser.getUser().getId();
		int id = Integer.parseInt(json.get("userId"));
		JSONObject jsonObject = new JSONObject();
		if(focusService.focus(EntityType.USER, userId, id)) {
			jsonObject.put("code", "关注成功");
		}
		else {
			jsonObject.put("code", "关注失败");
		}
		eventProducer.executeEvent(new EventModel().setType(EventType.FOCUS).setOwnerId(userId).setToId(id)
				.setEntityType(EntityType.USER).setEntityId(id));
		return jsonObject.toJSONString();
	}
	
	@RequestMapping(path= {"/unfocusUser"},method= {RequestMethod.POST})
	@ResponseBody
	public String unfocusUser(@RequestBody Map<String,String> json) {
		int userId = hostUser.getUser().getId();
		int id = Integer.parseInt(json.get("userId"));
		JSONObject jsonObject = new JSONObject();
		if(focusService.unfocus(EntityType.USER, userId, id)) {
			jsonObject.put("code", "关注成功");
		}
		else {
			jsonObject.put("code", "关注失败");
		}
		
		return jsonObject.toJSONString();
	}
	
	@RequestMapping(path= {"/focusQuestion"},method= {RequestMethod.POST})
	@ResponseBody
	public String focusQuestion(@RequestBody Map<String,String> json) {
		int userId = hostUser.getUser().getId();
		int id = Integer.parseInt(json.get("questionId"));
		JSONObject jsonObject = new JSONObject();
		if(focusService.focus(EntityType.QUESTION, userId, id)) {
			jsonObject.put("code", "关注成功");
		}
		else {
			jsonObject.put("code", "关注失败");
		}
		Question question = questionService.findById(id);
		eventProducer.executeEvent(new EventModel().setType(EventType.FOCUS).setOwnerId(userId).setToId(question.getUser_id())
				.setEntityType(EntityType.QUESTION).setEntityId(id));
		return jsonObject.toJSONString();
			
	}
	
	@RequestMapping(path= {"/unfocusQuestion"},method= {RequestMethod.POST})
	@ResponseBody
	public String unfocusQuestion(@RequestBody Map<String,String> json) {
		int userId = hostUser.getUser().getId();
		int id = Integer.parseInt(json.get("questionId"));
		JSONObject jsonObject = new JSONObject();
		if(focusService.unfocus(EntityType.QUESTION, userId, id)) {
			jsonObject.put("code", "关注成功");
		}
		else {
			jsonObject.put("code", "关注失败");
		}
		return jsonObject.toJSONString();
	}
}
