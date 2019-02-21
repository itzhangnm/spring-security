package com.zb.security.app;

import com.zb.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.zb.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zb.security.core.properties.SecurityConstants;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * oauth 资源服务器配置
 *
 * @author zb
 * @date 2019/2/13 13:54
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSOR_URL_FORM)
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.and()
			.apply(openIdAuthenticationSecurityConfig)
				.and()
			.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.apply(springSocialConfigurer)
				.and()
			.authorizeRequests()
				.antMatchers("/favicon.ico",
						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
						"/oauth/user", "/user/register","/social/signUp").permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf()
				.disable();
	}
}
