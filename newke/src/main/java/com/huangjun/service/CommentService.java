package com.huangjun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huangjun.dao.CommentDAO;
import com.huangjun.eunm.EntityType;
import com.huangjun.model.Comment;

@Service
public class CommentService{
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private FliterService filterService;
	
	public List<Comment> findAll(int entity_id, String entity_type, int start, int count) {
		// TODO Auto-generated method stub
		return commentDAO.findAll(entity_id, entity_type, start, count);
	}
	
	public int getAnswerCount(int userId,EntityType entityType) {
		return commentDAO.getAnswerCount(userId, entityType.toString());
	}
	
	public Comment findComment(int id) {
		return commentDAO.findComment(id);
	}

	public int addComment(Comment comment) {
		comment.setContent(filterService.fliterHtml(comment.getContent()));
		comment.setContent(filterService.fliterSensetive(comment.getContent()));
		return commentDAO.addComment(comment) > 0 ? comment.getId():0;
	}

	public int updateComment(int id, int status) {
		// TODO Auto-generated method stub
		return commentDAO.updateComment(id, status);
	}

	
	
	
	
	
}