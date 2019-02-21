package com.zb.security.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * {@link HandlerInterceptor} time log
 *
 * @author zb
 * @date 2018/12/18 16:49
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("timeInterceptor preHandle");
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("timeInterceptor postHandle");
		long startTime = (long) request.getAttribute("startTime");
		System.out.println("timeInterceptor postHandle time consuming:" + (System.currentTimeMillis() - startTime));
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("timeInterceptor afterCompletion");
		long startTime = (long) request.getAttribute("startTime");
		System.out.println("timeInterceptor afterCompletion time consuming:" + (System.currentTimeMillis() - startTime));
		Optional.ofNullable(ex).ifPresent(e -> System.out.println(e.getMessage()));
		
	}
}
