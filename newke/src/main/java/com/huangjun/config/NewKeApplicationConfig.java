package com.huangjun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.huangjun.interceptor.LoginRequiredInterceptor;
import com.huangjun.interceptor.PassportInterceptor;

@Component
public class NewKeApplicationConfig implements WebMvcConfigurer{
	@Autowired
	PassportInterceptor passportInterceptor;
	@Autowired
	LoginRequiredInterceptor loginRequiredInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor);
		registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
