package com.huangjun.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.async.EventModel;
import com.huangjun.dao.MessageDAO;
import com.huangjun.model.Message;
import com.huangjun.util.WendaUtil;

@Service
public class MessageService {
	@Autowired
	private FliterService fliterService;
	@Autowired
	private MessageDAO messageDAO;
	
	public List<Message> findAllConversation(int user_id,int start,int count){
		return messageDAO.findAllConversation(user_id, start, count);
	}
	
	
	public List<Message> findMessage(String conversation_id,int start, int count){
		return messageDAO.findMessage(conversation_id, start, count);
	}
	
	public int addMessage(Message message) {
		message.setContent(fliterService.fliterHtml(message.getContent()));
		message.setContent(fliterService.fliterSensetive(message.getContent()));
		return messageDAO.addMessage(message);
	}
	

	
	public int deleteConversation(String conversation_id) {
		return 	messageDAO.deleteConversation(conversation_id);
	}
	
	
	
	
}
