package com.huangjun.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huangjun.async.EventModel;
import com.huangjun.async.EventProducer;
import com.huangjun.eunm.EntityType;
import com.huangjun.eunm.EventType;
import com.huangjun.model.Comment;
import com.huangjun.model.HostUser;
import com.huangjun.model.Question;
import com.huangjun.service.CommentService;
import com.huangjun.service.QuestionService;

@Controller
public class CommentController {
	@Autowired
	private HostUser hostUser;
	@Autowired
	private CommentService commentService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private EventProducer eventProducer;
	@RequestMapping(path= {"/addComment"},method= {RequestMethod.POST})
	public String addComment(@RequestParam("questionId") String qid,@RequestParam("content") String content) {
		if(hostUser.getUser()==null) {
			return "redirect:/";
		}else {
			int questionId = Integer.parseInt(qid);
			Comment comment = new Comment();
			comment.setCreated_date(new Date());
			comment.setStatus(1);
			comment.setContent(content);
			comment.setEntity_id(questionId);
			comment.setEntity_type(EntityType.QUESTION.toString());
			comment.setUser_id(hostUser.getUser().getId());
			commentService.addComment(comment);
			Question question = questionService.findById(questionId);
			question.setComment_count(question.getComment_count()+1);
			questionService.updateQuestion(question);
			eventProducer.executeEvent(new EventModel().setEntityType(EntityType.QUESTION).setOwnerId(hostUser.getUser().getId())
					.setType(EventType.COMMENT).setEntityId(questionId).setToId(question.getUser_id()));
			return "redirect:question/"+qid;
		}
	}
	
	
}
