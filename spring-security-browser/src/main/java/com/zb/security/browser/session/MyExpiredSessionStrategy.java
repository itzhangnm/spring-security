package com.zb.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 用户session超越并发数策略
 *
 * @author zb
 * @date 2019/1/30 14:47
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {
	
	
	public MyExpiredSessionStrategy(String sessionInvalidUrl, String suffix) {
		super(sessionInvalidUrl, suffix);
	}
	
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(),event.getResponse());
	}
	
	@Override
	protected boolean isConcurrent() {
		return true;
	}
}
