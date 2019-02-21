package com.zb.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登陆配置
 *
 * @author zb
 * @date 2019/1/17 14:37
 */
@Component("smsCodeAuthenticationSecurityConfig")
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);
		SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(userDetailsService);
		http.authenticationProvider(provider)
				.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
