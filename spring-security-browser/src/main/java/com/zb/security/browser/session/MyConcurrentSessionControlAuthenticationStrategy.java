package com.zb.security.browser.session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.social.connect.web.SessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author zb
 * @date 2019/1/30 17:38
 */
public class MyConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy implements ObjectPostProcessor<ConcurrentSessionControlAuthenticationStrategy> {
	
	
	public MyConcurrentSessionControlAuthenticationStrategy(boolean exceptionIfMaximumExceeded, int maximumSessions, SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		super.setExceptionIfMaximumExceeded(exceptionIfMaximumExceeded);
		super.setMaximumSessions(maximumSessions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <O extends ConcurrentSessionControlAuthenticationStrategy> O postProcess(O object) {
		return (O) this;
	}
	
	@Override
	protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
		System.out.println("????");
		super.allowableSessionsExceeded(sessions, allowableSessions, registry);
	}
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("????");
		super.onAuthentication(authentication, request, response);
	}
}
