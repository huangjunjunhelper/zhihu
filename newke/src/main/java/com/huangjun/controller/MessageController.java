package com.huangjun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huangjun.model.HostUser;
import com.huangjun.model.Message;
import com.huangjun.service.MessageService;
import com.huangjun.service.UserService;

@Controller
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private HostUser hostUser;
	@Autowired
	private UserService userService;
	
	@RequestMapping(path= {"/addMessage"},method= {RequestMethod.POST})
	public String addMessage(@RequestParam("name") String name,@RequestParam("content") String content) {
		Message message = new Message();
		message.setContent(content);
		message.setFrom_id(hostUser.getUser().getId());
		message.setTo_id(userService.findByName(name).getId());
		message.setConversation_id();
		message.setCreated_date(new Date());
		message.setHas_read(0);
		messageService.addMessage(message);
		return "redirect:/index";
	}
	
	@RequestMapping(path= {"/letter"}, method= {RequestMethod.GET})
	public String letter(Model model) {
		List<Map<String,Object>> views = new ArrayList<Map<String,Object>>();
		List<Message> messages = messageService.findAllConversation(hostUser.getUser().getId(), 0, 10);
		for(Message message:messages) {
			Map<String, Object> view = new HashMap<String, Object>();
			view.put("message", message);
			int user_id = message.getFrom_id() == hostUser.getUser().getId() ? message.getTo_id():message.getFrom_id();
			view.put("user", userService.findById(user_id));
			views.add(view);
		}
		model.addAttribute("views",views);
		return "letter";
	}
	
	@RequestMapping(path= {"/letterDetail/{conversation_id}"}, method= {RequestMethod.GET})
	public String letterDetail(Model model, @PathVariable("conversation_id") String conversation_id) {
		List<Map<String,Object>> views = new ArrayList<Map<String,Object>>();
		List<Message> messages = messageService.findMessage(conversation_id, 0, 10);
		for(Message message:messages) {
			Map<String, Object> view = new HashMap<String, Object>();
			view.put("message", message);
			view.put("user", userService.findById(message.getFrom_id()));
			views.add(view);
		}
		model.addAttribute("views", views);
		return "/letterDetail";
	}
	
	
}
