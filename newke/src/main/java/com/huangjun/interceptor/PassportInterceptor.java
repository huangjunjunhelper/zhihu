package com.huangjun.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huangjun.dao.TicketDAO;
import com.huangjun.dao.UserDAO;
import com.huangjun.model.HostUser;
import com.huangjun.model.Ticket;
import com.huangjun.model.User;

@Component
public class PassportInterceptor implements HandlerInterceptor{
	@Autowired
	private TicketDAO ticketDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private HostUser hostUser;
	
	@Override
	public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("ticket")) {
				ticket = cookie.getValue();
				break;
			}
		}
		if(ticket!=null) {
			Ticket t  = ticketDAO.findByTicket(ticket);
			if(t==null||t.getExpired().before(new Date())||t.getStatus()==0) {
				return true;
			}
			int user_id = t.getUser_id();
			User user = userDAO.findById(user_id);
			hostUser.setUser(user);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,@Nullable ModelAndView modelAndView) throws Exception {
		if(hostUser.getUser()!=null&&modelAndView!=null)
			modelAndView.addObject("self", hostUser.getUser());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,@Nullable Exception ex) throws Exception {
		hostUser.clear();
	}
}
