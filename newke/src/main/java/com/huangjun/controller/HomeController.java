package com.huangjun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huangjun.eunm.EntityType;
import com.huangjun.model.Feed;
import com.huangjun.model.HostUser;
import com.huangjun.model.Question;
import com.huangjun.model.User;
import com.huangjun.service.CommentService;
import com.huangjun.service.FeedService;
import com.huangjun.service.FocusService;
import com.huangjun.service.LikeService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;

 

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FocusService focusService;
    @Autowired
    private HostUser hostUser;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FeedService feedService;
    
    @RequestMapping(path = {"/index"})
    public String index(Model model){
    	User self = hostUser.getUser();
//    	List<Question> questions = questionService.findAll(0, 0, 10);
//    	List<Map<String,Object>> views = new ArrayList<>();
//    	for(Question question:questions) {
//    		Map<String,Object> map = new HashMap<>();
//    		map.put("question", question);
//    		map.put("user", userService.findById(question.getUser_id()));
//    		map.put("likeCount", likeService.getLikeCount(EntityType.QUESTION, question.getId()));
//    		map.put("isFocus", focusService.isFocus(EntityType.QUESTION, self.getId(), question.getId()));
//    		views.add(map);
//    	}
//    	model.addAttribute("views", views);
//    	model.addAttribute("focusCount", focusService.getFocusCount(EntityType.USER, self.getId()));
		int localUserId = self.getId();
		List<Integer> focus = focusService.getFocus(EntityType.USER, localUserId, 0, Integer.MAX_VALUE);
		List<Feed> feeds = feedService.getFeeds(focus, 0, 10);
		System.out.println(feeds.size());
		model.addAttribute("feeds", feeds);
    	List<Map<String,Object>> focusInfo = new ArrayList<>();
    	for(Integer i:focusService.getFocus(EntityType.USER, self.getId(), 0, 10)) {
    		Map<String,Object> focusMap = new HashMap<String, Object>();
    		focusMap.put("user", userService.findById(i));
    		focusMap.put("focusCount", focusService.getFocusCount(EntityType.USER, i));
    		focusMap.put("isFocus", focusService.isFocus(EntityType.USER, self.getId(), i));
    		focusMap.put("questionCount",questionService.getCount(i));
    		focusMap.put("answerCount", commentService.getAnswerCount(i, EntityType.QUESTION));
    		focusInfo.add(focusMap);
    	}
    	model.addAttribute("focusInfo", focusInfo);
    	return "index";
    }
    

   
    
    

}

