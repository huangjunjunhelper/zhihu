package com.huangjun.util;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

@Component
public class JedisUtil implements InitializingBean{
	private JedisPool pool;


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool = new JedisPool(new JedisPoolConfig(), "localhost");
	}
	
	public Long sadd(String key,String value) {
		try(Jedis jedis = pool.getResource()){
			return jedis.sadd(key, value);
		}
		
	}
	
	public long srem(String key,String value) {
		try(Jedis jedis = pool.getResource()){
			return jedis.srem(key, value);
		}
	}
	
	public long scard(String key) {
		try(Jedis jedis = pool.getResource()){
			return jedis.scard(key);
		}
	}
	
	public boolean sismember(String key,String value) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.sismember(key, value);
		}
	}
	
	public Long lpush(String key,String value) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.lpush(key, value);
		}
	}
	
	public List<String> brpop(String key) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.blpop(1000, key);
		}
	}
	
	public Jedis getJedis() {
		return pool.getResource();
	}
	
	public Transaction multi(Jedis jedis) {
		return  jedis.multi();

	}
	
	public List<Object> exec(Transaction transaction,Jedis jedis) {
			try {
				return transaction.exec();
			}catch(Exception e) {
				transaction.discard();
			}finally{
				if(transaction!=null)
					try {
						transaction.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(jedis!=null)
					jedis.close();
			}
			return null;
	}

	
	
	public long zadd(String key,double score, String member) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.zadd(key, score, member);
		}
	}
	
	public long zrem(String key,String member) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.zrem(key, member);
		}
	}
	
	public long zcard(String key) {
		try(Jedis jedis = pool.getResource()){
			return  jedis.zcard(key);
		}
	}
	
	public long zrank(String key,String member) {
		try(Jedis jedis = pool.getResource()){
			if(jedis.zrank(key,member)==null)
				return -1;
			else
				return  jedis.zrank(key,member);
		}
	}
	
	public Set<String> zrange(String key,long start,long end){
		try(Jedis jedis = pool.getResource()){
			return  jedis.zrange(key, start, end);
		}
	}
 
	
	
	
	
	
	
	
}
