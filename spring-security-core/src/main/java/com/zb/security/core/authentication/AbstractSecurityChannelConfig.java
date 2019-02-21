package com.zb.security.core.authentication;

import com.zb.security.core.properties.BrowserProperties;
import com.zb.security.core.properties.SecurityConstants;
import com.zb.security.core.properties.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 抽象配置Security类
 *
 * @author zb
 * @date 2019/1/17 18:27
 */
public class AbstractSecurityChannelConfig extends WebSecurityConfigurerAdapter {
	
	protected final AuthenticationSuccessHandler authenticationSuccessHandler;
	
	protected final AuthenticationFailureHandler authenticationFailureHandler;
	
	protected final SecurityProperties securityProperties;
	
	public AbstractSecurityChannelConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.securityProperties = securityProperties;
	}
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_AUTHENTICATION_REQUIRE_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSOR_URL_FORM)
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler);
	}
}
