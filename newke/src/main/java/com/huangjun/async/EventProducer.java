package com.huangjun.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.util.JedisUtil;
import com.huangjun.util.RedisKeyUtil;

@Component
public class EventProducer {
	@Autowired
	private JedisUtil jedisUtil;
	
	public void executeEvent(EventModel event) {
		String key = RedisKeyUtil.getEventKey();
		String eventJson = JSONObject.toJSONString(event);
		jedisUtil.lpush(key, eventJson);
	}
}
