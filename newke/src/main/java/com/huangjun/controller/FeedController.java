package com.huangjun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.eunm.EntityType;
import com.huangjun.model.Feed;
import com.huangjun.model.HostUser;
import com.huangjun.model.User;
import com.huangjun.service.FeedService;
import com.huangjun.service.FocusService;

@Controller
public class FeedController {
	@Autowired
	private HostUser hostUser;
	@Autowired
	private FeedService feedService;
	@Autowired
	private FocusService focusService;
	
	@RequestMapping(path="/pullFeed",method= {RequestMethod.GET})
	public String pullFeed(Model model) {
		User user = hostUser.getUser();
		int localUserId = user.getId();
		List<Integer> focus = focusService.getFocus(EntityType.USER, localUserId, 0, Integer.MAX_VALUE);
		List<Feed> feeds = feedService.getFeeds(focus, 0, 10);
		model.addAttribute("feeds",feeds);
		return "recentThing";
	}
	
	@RequestMapping(path="/pushFeed",method= {RequestMethod.GET})
	public String pushFeed(Model model) {
		User user = hostUser.getUser();
		int localUserId = user.getId();
		List<Object> list = feedService.getFeeds(localUserId, 0, 10);
		System.out.println(list.size());
		model.addAttribute("feeds", list);
		return "recent";
	}
}
