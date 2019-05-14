package com.huangjun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.huangjun.eunm.EntityType;
import com.huangjun.service.FocusService;
import com.huangjun.service.LikeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitialTests {
	@Autowired
	private FocusService focusService;
	
	
	@Test
	public void addLikeUser() {
		focusService.focus(EntityType.USER, 26, 27);
		focusService.focus(EntityType.USER, 26, 28);
		focusService.focus(EntityType.USER, 26, 29);
		focusService.focus(EntityType.USER, 26, 30);
		focusService.focus(EntityType.USER, 26, 31);
		focusService.focus(EntityType.USER, 2, 26);
		focusService.focus(EntityType.USER, 2, 27);
		focusService.focus(EntityType.USER, 2, 35);
		
	}
}
