package com.zb.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户session过期策略
 *
 * @author zb
 * @date 2019/1/30 14:46
 */
public class MyInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
	
	
	public MyInvalidSessionStrategy(String sessionInvalidUrl, String suffix) {
		super(sessionInvalidUrl, suffix);
	}
	
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		onSessionInvalid(request,response);
	}
}
