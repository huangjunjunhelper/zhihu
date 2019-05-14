package com.huangjun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.Feed;

public interface FeedDAO {
	Feed findById(@Param("id") int id);
	List<Feed> findByUserIds(@Param("userIds") List<Integer> userIds,@Param("start") int start,@Param("count") int count);
	int addFeed(Feed feed);
}
