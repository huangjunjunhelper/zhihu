package com.huangjun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.huangjun.model.User;
import com.huangjun.service.CommentService;
import com.huangjun.service.FocusService;
import com.huangjun.service.LikeService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private HostUser hostuser;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private EventProducer eventProducer;
			
	@RequestMapping(path= {"/question/add"},method= {RequestMethod.POST})
	public String question(Model model,@RequestParam("title") String title,@RequestParam("content") String content) {
			User localUser = hostuser.getUser();
            Question question = new Question();
            question.setContent(content);
            question.setCreated_date(new Date());
            question.setTitle(title);
            question.setUser_id(localUser.getId());    
            questionService.addQuestion(question);
            eventProducer.executeEvent(new EventModel().setOwnerId(localUser.getId()).setType(EventType.QUESTION)
            							.setEntityType(EntityType.QUESTION)
            							.setEntityId(question.getId()));
            return "redirect:/index";
           
            
          
    }
	
	@RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
	public String questionDetail(Model model,@PathVariable("qid") int qid) {
		List<Comment> comments = commentService.findAll(qid, "question", 0, 10);
		List<Map<String,Object>> views = new ArrayList<Map<String,Object>>();
		for(Comment comment:comments) {
			Map<String,Object> view = new HashMap<String, Object>();
			view.put("comment", comment);
			view.put("user", userService.findById(comment.getUser_id()));
			view.put("likeCount", likeService.getLikeCount(EntityType.COMMENT, comment.getId()));
			views.add(view);
		}
		model.addAttribute("views", views);
		model.addAttribute("question", questionService.findById(qid));
		model.addAttribute("focusCount",focusService.getFansCount(EntityType.QUESTION, qid));
		model.addAttribute("isFocus", focusService.isFocus(EntityType.QUESTION, hostuser.getUser().getId(), qid));
		List<User> fans = new ArrayList<>();
		List<Integer> fanIds = focusService.getFans(EntityType.QUESTION, qid, 0, 10);
		for(Integer i:fanIds) {
			fans.add(userService.findById(i));
		}
		model.addAttribute("fans",fans);
		return "detail";
	}


}

