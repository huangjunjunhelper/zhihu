package com.huangjun.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.dao.FeedDAO;
import com.huangjun.model.Feed;
import com.huangjun.util.JedisUtil;
import com.huangjun.util.RedisKeyUtil;
import com.huangjun.util.WendaUtil;

@Service
public class FeedService {
	@Autowired
	private FeedDAO feedDAO;
	@Autowired
	private JedisUtil jedisUtil;
	
	public Feed getFeed(int id) {
		return feedDAO.findById(id);
	}
	
	public List<Feed> getFeeds(List<Integer> userIds,int start,int count){
		return feedDAO.findByUserIds(userIds, start, count);
	}
	
	public int addFeed(Feed feed) {
		return feedDAO.addFeed(feed);
	}
	
	public long addFeed(int id,String member) {
		String key = RedisKeyUtil.getFeedKey(id);
		return jedisUtil.zadd(key, new Date().getTime(), member);
	}
	
	public List<Object> getFeeds(int id,int start,int count){
		String key = RedisKeyUtil.getFeedKey(id);
		List<String> list = WendaUtil.setToListString(jedisUtil.zrange(key, start, start+count));	
		return FeedService.toObjectArray(list);
	}
	
	private static List<Object> toObjectArray(List<String> list) {
		List<Object> l = new ArrayList<>();
		for(String s:list) {
			l.add(JSONObject.parse(s));
		}
		return l;
	}
	
}
