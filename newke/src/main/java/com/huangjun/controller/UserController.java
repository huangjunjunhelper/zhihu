package com.huangjun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huangjun.eunm.EntityType;
import com.huangjun.model.HostUser;
import com.huangjun.model.Question;
import com.huangjun.model.User;
import com.huangjun.service.CommentService;
import com.huangjun.service.FocusService;
import com.huangjun.service.LikeService;
import com.huangjun.service.QuestionService;
import com.huangjun.service.UserService;

@Controller
public class UserController {
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


	@RequestMapping(path= {"/"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(path= {"/reg"},method= {RequestMethod.POST})
	public String register(HttpServletResponse response ,Model model,@RequestParam("userName") String userName,
			@RequestParam("password") String password) {
		Map<String,String> map = userService.register(userName, password);
		if(map.containsKey("ticket")) {
			String ticket = map.get("ticket");
			Cookie cookie = new Cookie("ticket",ticket);
			response.addCookie(cookie);
		}
		if(map.containsKey("msg")) {
			model.addAttribute("msg", map.get("msg").toString());
			return "login";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(path= {"/login"},method= {RequestMethod.POST})
	public String login(HttpServletResponse response,Model model,
						@RequestParam("userName") String userName,
						@RequestParam("password") String password) {
		Map<String,String> map = userService.login(userName, password);
		if(map.containsKey("ticket")) {
			String ticket = map.get("ticket");
			Cookie cookie = new Cookie("ticket", ticket);
			response.addCookie(cookie);
		}
		if(map.containsKey("msg")) {
			model.addAttribute("msg", map.get("msg").toString());
			return "login";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(path= {"/signOut"})
	public String signOut(HttpServletRequest request,HttpServletResponse response) {
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies) {
			if("ticket".equals(cookie.getName())) {
				ticket = cookie.getValue();
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		userService.signOut(ticket);
		return "redirect:/";
	}
	
	@RequestMapping(path= {"/user/{qid}"})
	public String userHome(Model model,@PathVariable("qid") int qid) {
    	List<Question> questions = questionService.findAll(qid,0,10);
    	List<Map<String,Object>> views = new ArrayList<>();
    	for(Question question:questions) {
    		Map<String,Object> map = new HashMap<>();
    		map.put("question", question);
    		map.put("user", userService.findById(question.getUser_id()));
    		map.put("likeCount", likeService.getLikeCount(EntityType.QUESTION, question.getId()));
    		map.put("isFocus", focusService.isFocus(EntityType.QUESTION, hostUser.getUser().getId(), question.getId()));
    		views.add(map);
    	}
    	model.addAttribute("views", views);
    	User user = userService.findById(qid);
    	model.addAttribute("theUser",user);
    	model.addAttribute("isFocus",focusService.isFocus(EntityType.USER, hostUser.getUser().getId(), qid));
    	model.addAttribute("focusCount", focusService.getFocusCount(EntityType.USER, qid));
    	List<Map<String,Object>> focusInfo = new ArrayList<>();
    	for(Integer i:focusService.getFocus(EntityType.USER, qid, 0, 10)) {
    		Map<String,Object> focusMap = new HashMap<String, Object>();
    		focusMap.put("user", userService.findById(i));
    		focusMap.put("focusCount", focusService.getFocusCount(EntityType.USER, i));
    		focusMap.put("isFocus", focusService.isFocus(EntityType.USER,hostUser.getUser().getId(), i));
    		focusMap.put("questionCount",questionService.getCount(i));
    		focusMap.put("answerCount", commentService.getAnswerCount(i, EntityType.QUESTION));
    		focusInfo.add(focusMap);
    	}
    	model.addAttribute("focusInfo", focusInfo);
    	return "otherUser";
	}
}
