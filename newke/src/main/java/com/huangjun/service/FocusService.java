package com.huangjun.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.eunm.EntityType;
import com.huangjun.util.JedisUtil;
import com.huangjun.util.RedisKeyUtil;
import com.huangjun.util.WendaUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Service
public class FocusService {
	@Autowired
	private JedisUtil jedisUtil;
	
	public boolean focus(EntityType entityType,int userId,int id) {
		String focusKey = RedisKeyUtil.getFocusKey(entityType.toString(), userId);
		String fansKey = RedisKeyUtil.getFansKey(entityType.toString(), id);
		Jedis jedis = jedisUtil.getJedis();
		Transaction transaction = jedisUtil.multi(jedis);
		transaction.zadd(focusKey, new Date().getTime(), String.valueOf(id));
		transaction.zadd(fansKey, new Date().getTime(), String.valueOf(userId));
		List<Object> list = jedisUtil.exec(transaction,jedis);
		List<Integer> l = WendaUtil.toListInteger(list);
		return l.size()==2&&l.get(0)>0&&l.get(1)>0;
	}
	
	public boolean unfocus(EntityType entityType,int userId,int id) {
		String focusKey = RedisKeyUtil.getFocusKey(entityType.toString(), userId);
		String fansKey = RedisKeyUtil.getFansKey(entityType.toString(), id);
		Jedis jedis = jedisUtil.getJedis();
		Transaction transaction = jedisUtil.multi(jedis);
		transaction.zrem(focusKey, String.valueOf(id));
		transaction.zrem(fansKey, String.valueOf(userId));
		List<Object> list = jedisUtil.exec(transaction,jedis);
		List<Integer> l = WendaUtil.toListInteger(list);
		return l.size()==2&&l.get(0)>0&&l.get(1)>0;
	}
	
	public List<Integer> getFocus(EntityType entityType,int userId,int start,int end){
		String focusKey = RedisKeyUtil.getFocusKey(entityType.toString(), userId);
		Set<String> set =  jedisUtil.zrange(focusKey, start, end);
		List<Integer> list = WendaUtil.setToList(set);
		return list;
	}
	
	public List<Integer> getFans(EntityType entityType,int userId,int start,int end){
		String fansKey = RedisKeyUtil.getFansKey(entityType.toString(), userId);
		Set<String> set =  jedisUtil.zrange(fansKey, start, end);
		List<Integer> list = WendaUtil.setToList(set);
		return list;
	}
	
	public long getFocusCount(EntityType entityType,int userId) {
		String focusKey = RedisKeyUtil.getFocusKey(entityType.toString(), userId);
		return jedisUtil.zcard(focusKey);
	}
	
	public long getFansCount(EntityType entityType,int userId) {
		String fansKey = RedisKeyUtil.getFansKey(entityType.toString(), userId);
		return jedisUtil.zcard(fansKey);
	}
	
	public boolean isFocus(EntityType entityType,int userId,int id) {
		String focusKey = RedisKeyUtil.getFocusKey(entityType.toString(), userId);
		return jedisUtil.zrank(focusKey, String.valueOf(id)) >= 0 ? true:false;
	}
	
	
}
