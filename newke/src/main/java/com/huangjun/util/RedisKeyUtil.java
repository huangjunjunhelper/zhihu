package com.huangjun.util;

public class RedisKeyUtil {
	private final static String SPLIT = ":";
	private final static String DISLIKE = "DISLIKE";
	private final static String LIKE = "LIKE";
	private final static String EVENT = "EVENT";
	private final static String FOCUS = "FOCUS";
	private final static String FANS = "FANS";
	private final static String FEED = "FEED";
	
	public static String getLikeKey(String type,int id) {
		return LIKE+SPLIT+type+SPLIT+String.valueOf(id);
	}
	
	public static String getDislikeKey(String type,int id) {
		return DISLIKE+SPLIT+type+SPLIT+String.valueOf(id);
	}
	
	public static String getEventKey() {
		return EVENT;
	}
	
	public static String getFocusKey(String type,int id) {
		return FOCUS+SPLIT+type+SPLIT+String.valueOf(id);
	}
	
	public static String getFansKey(String type,int id) {
		return FANS+SPLIT+type+SPLIT+String.valueOf(id);
	}
	
	public static String getFeedKey(int id) {
		return FEED+SPLIT+String.valueOf(id);
	}
	
	
}

