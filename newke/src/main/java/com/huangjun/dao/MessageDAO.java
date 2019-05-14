package com.huangjun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.Message;

public interface MessageDAO {
	List<Message> findAllConversation(@Param("user_id") int user_id,@Param("start") int start,@Param("count") int count);
	List<Message> findMessage(@Param("conversation_id") String conversation_id,@Param("start") int start,@Param("count") int count);
    int addMessage(Message Message);
    int deleteConversation(@Param("conversation_id") String conversation_id);
}
