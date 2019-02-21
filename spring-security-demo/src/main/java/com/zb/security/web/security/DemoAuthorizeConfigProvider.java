package com.zb.security.web.security;

import com.zb.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zb
 * @date 2019/2/21 14:05
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers("/user").hasRole("ADMIN");
	}
}
