package com.huangjun.dao;

import org.apache.ibatis.annotations.Param;

import com.huangjun.model.Ticket;

public interface TicketDAO {
	Ticket findByTicket(@Param("ticket") String ticket);
	int addTicket(Ticket ticket);
	int deleteTicket(@Param("ticket") String ticket);
	int deleteAll();
}
