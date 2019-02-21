package com.zb.security.app.authentication.openid;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * openId security 配置
 *
 * @author zb
 * @date 2019/2/18 10:13
 */
@Component("openIdAuthenticationSecurityConfig")
@RequiredArgsConstructor
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {
	
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	
	private final UsersConnectionRepository usersConnectionRepository;
	
	private final SocialUserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		OpenIdAuthenticationFilter openFilter = new OpenIdAuthenticationFilter();
		openFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		openFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		openFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		OpenIdAuthenticationProvider openProvider = new OpenIdAuthenticationProvider(userDetailsService,usersConnectionRepository);
		http.authenticationProvider(openProvider)
				.addFilterBefore(openFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
