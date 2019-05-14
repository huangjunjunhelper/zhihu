package com.huangjun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.dao.QuestionDAO;
import com.huangjun.model.Question;

@Service
public class QuestionService{
	
	@Autowired
	private QuestionDAO questionDAO;
	@Autowired
	private FliterService fliterService;
	
	public List<Question> findAll(int userId,int start,int count) {
		// TODO Auto-generated method stub
		return questionDAO.findAll(userId,start,count);
	}

	public Question findById(int id) {
		// TODO Auto-generated method stub
		return questionDAO.findById(id);
	}
	
	public int getCount(int userId) {
		return questionDAO.getCount(userId);
	}

	public int addQuestion(Question question) {
		question.setTitle(fliterService.fliterHtml(question.getTitle()));
		question.setContent(fliterService.fliterHtml(question.getContent()));
		question.setTitle(fliterService.fliterSensetive(question.getTitle()));
		question.setContent(fliterService.fliterSensetive(question.getContent()));
		return questionDAO.addQuestion(question);
	}

	public int updateQuestion(Question Question) {
		return questionDAO.updateQuestion(Question);
	}

	public int deleteQuestion(int id) {
		return questionDAO.deleteQuestion(id);
	}
	
	
}