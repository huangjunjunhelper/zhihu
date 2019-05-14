package com.huangjun.async;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huangjun.eunm.EventType;
import com.huangjun.util.JedisUtil;
import com.huangjun.util.RedisKeyUtil;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
	private ApplicationContext applicationContext;
	private Map<EventType,Set<EventHandler>>  config = new HashMap<>();
	@Autowired
	private JedisUtil jedisUtil;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, EventHandler>  beans = applicationContext.getBeansOfType(EventHandler.class);
		for(EventHandler handler:beans.values()) {
			List<EventType> types = handler.getSupportEventType();
			for(EventType type:types) {
				if(!config.containsKey(type))
					config.put(type, new HashSet<>());
				config.get(type).add(handler);
			}
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					String key = RedisKeyUtil.getEventKey();
					List<String> list = jedisUtil.brpop(key);
					String json = list.get(1);
					EventModel event = JSONObject.parseObject(json, EventModel.class);
					Set<EventHandler> handlers = config.get(event.getType());
					if(handlers!=null) {
						for(EventHandler handler:handlers) {
							if(handler!=null)
								handler.handle(event);
						}
					}
						
				}
			}
		}).start();
	
	}
}
