package com.huangjun.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.huangjun.async.EventProducer;
import com.huangjun.dao.TicketDAO;
import com.huangjun.dao.UserDAO;
import com.huangjun.model.Ticket;
import com.huangjun.model.User;
import com.huangjun.util.WendaUtil;

@Service
public class UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private TicketDAO ticketDAO;
	@Autowired
	private EventProducer eventProducer;
	
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userDAO.findAll();
	}

	public User findById(int id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id);
	}
	
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userDAO.findByName(name);
	}

	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userDAO.updateUser(user);
	}

	public int deleteUser(int id) {
		// TODO Auto-generated method stub
		return userDAO.deleteUser(id);
	}

	public Map<String, String> register(String userName, String password) {
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isEmptyOrWhitespace(userName)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if(StringUtils.isEmptyOrWhitespace(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		if(userName.length()<5||userName.length()>15) {
			map.put("msg", "用户名长度必须在5-15之内");
			return map;
		}
		if(password.length()<5||password.length()>15) {
			map.put("msg", "密码长度必须在5-15之内");
			return map;
		}
		if(userDAO.findByName(userName)!=null) {
			map.put("msg", "该用户名已被注册");
			return map;
		}
		//生成user
		User user = new User();
		user.setName(userName);
		String salt = UUID.randomUUID().toString();
		System.out.println(salt);
		user.setSalt(salt);
		user.setPassword(WendaUtil.MD5(password+salt));
		user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(100)));
		userDAO.addUser(user);	
		
		//生成ticket
		String ticket = getTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}

	public Map<String, String> login(String userName, String password) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isEmptyOrWhitespace(userName)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if(StringUtils.isEmptyOrWhitespace(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		User user =  userDAO.findByName(userName);
		if(user==null) {
			map.put("msg", "用户名错误");
			return map;
		}
		String raw = user.getPassword();
		String salt = user.getSalt();
		if(!raw.equals(WendaUtil.MD5(password+salt))) {
			map.put("msg", "密码错误");
			return map;
		}
		String ticket = getTicket(user.getId());
		map.put("ticket", ticket);
//		eventProducer.executeEvent(new EventModel(EventType.LOGIN).setEx("username", user.getName()));
		return map;
	}
	
	public int signOut(String  ticket) {
		return ticketDAO.deleteTicket(ticket);
	}
	
	private String getTicket(int user_id) {
		Ticket ticket = new Ticket();
		ticket.setUser_id(user_id);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		ticket.setExpired(calendar.getTime());
		String t = UUID.randomUUID().toString();
		ticket.setTicket(t);
		ticket.setStatus(1);
		ticketDAO.addTicket(ticket);
		return t;
	}

	
	
	
}