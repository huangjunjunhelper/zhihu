package com.huangjun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.Question;

public interface QuestionDAO {
	List<Question> findAll(@Param("userId") int userId,@Param("start") int start,@Param("count") int count);
	int getCount(@Param("userId") int userId);
	Question findById(@Param("id") int id);
	
	int addQuestion(Question question);
	int updateQuestion(Question question);
	int deleteQuestion(@Param("id") int id);
}
