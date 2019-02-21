package com.zb.security.core.validate;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.code.ValidateCodeFilter;
import com.zb.security.core.validate.code.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 验证码配置类
 *
 * @author zb
 * @date 2019/1/17 18:33
 */
@Component("validateCodeConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(failureHandler, securityProperties, validateCodeProcessorHolder);
		validateCodeFilter.afterPropertiesSet();
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
