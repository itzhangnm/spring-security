package com.zb.security.app.social;

import com.zb.security.core.social.SocialAuthenticationFilterPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * social filter 后置处理实现
 *
 * @author zb
 * @date 2019/2/18 10:55
 */

@RequiredArgsConstructor
public class MySocialAuthenticationFilterPostProcessorImpl implements SocialAuthenticationFilterPostProcessor {

	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
	public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
		socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		socialAuthenticationFilter.setSignupUrl("/social/signUp");
	}
}
