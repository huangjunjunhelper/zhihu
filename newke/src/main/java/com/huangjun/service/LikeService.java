package com.huangjun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.eunm.EntityType;
import com.huangjun.util.JedisUtil;
import com.huangjun.util.RedisKeyUtil;

@Service
public class LikeService {
	@Autowired
	private JedisUtil  jedisUtil;

	
	public long like(int userId,EntityType entityType,int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType.toString(), entityId);
		String dislikeKey = RedisKeyUtil.getDislikeKey(entityType.toString(), entityId);
		long count = jedisUtil.scard(likeKey);
		if(jedisUtil.sismember(likeKey, String.valueOf(userId)))
			return count;
		else if(jedisUtil.sismember(dislikeKey, String.valueOf(userId))) {
			jedisUtil.srem(dislikeKey, String.valueOf(userId));
			jedisUtil.sadd(likeKey, String.valueOf(userId));
		}
		else{
			jedisUtil.sadd(likeKey, String.valueOf(userId));
		}

		return count+1;
	}
	
	public long dislike(int userId,EntityType entityType,int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType.toString(), entityId);
		String dislikeKey = RedisKeyUtil.getDislikeKey(entityType.toString(), entityId);
		long count = jedisUtil.scard(likeKey);
		if(jedisUtil.sismember(dislikeKey, String.valueOf(userId)))
			return count;
		else if(jedisUtil.sismember(likeKey, String.valueOf(userId))) {
			jedisUtil.srem(likeKey, String.valueOf(userId));
			jedisUtil.sadd(dislikeKey, String.valueOf(userId));
		}
		else{
			jedisUtil.sadd(dislikeKey, String.valueOf(userId));
		}
		return  count ==0 ? 0:count-1;
	}
	
	public long getLikeCount(EntityType entityType,int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType.toString(), entityId);
		return jedisUtil.scard(likeKey);
	}
	
	
}
