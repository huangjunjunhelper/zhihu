package com.huangjun.async.Handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.async.EventHandler;
import com.huangjun.async.EventModel;
import com.huangjun.eunm.EntityType;
import com.huangjun.eunm.EventType;
import com.huangjun.model.Message;
import com.huangjun.service.MessageService;
import com.huangjun.util.WendaUtil;

@Service
public class LikeHandler implements EventHandler{
	
	@Autowired
	private MessageService messageService;
	@Override
	public void handle(EventModel event) {
		Message message = new Message();
		message.setFrom_id(WendaUtil.SYSTEM_USER_ID);
		message.setTo_id(event.getToId());
		message.setConversation_id();
		message.setCreated_date(new Date());
		message.setHas_read(0);
		if(event.getEntityType().equals(EntityType.COMMENT)) {
			message.setContent("你的评论收到了一个赞");
			messageService.addMessage(message);
		}
		else if(event.getEntityType().equals(EntityType.QUESTION)) {
			message.setContent("你的问题收到了一个赞");
			messageService.addMessage(message);
		}
	
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.LIKE);
	}

}
