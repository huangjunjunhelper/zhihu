package com.huangjun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.Comment;

public interface CommentDAO {
	 	List<Comment> findAll(@Param("entity_id") int entity_id,@Param("entity_type") String entity_type,
	 							@Param("start") int start,@Param("count") int count);
	 	int getAnswerCount(@Param("userId") int userId,@Param("entityType") String entityType);
	 	Comment findComment(@Param("id") int id);
	    int addComment(Comment Comment);
	    int updateComment(@Param("id") int id,@Param("status") int status);
}
