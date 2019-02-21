package com.zb.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信验证码认证 provider
 *
 * @author zb
 * @date 2019/1/16 11:19
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailsService userDetailsService;
	
	public SmsCodeAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		UserDetails user = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
		if (user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
		authenticationTokenResult.setDetails(authenticationToken.getDetails());
		return authenticationTokenResult;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
