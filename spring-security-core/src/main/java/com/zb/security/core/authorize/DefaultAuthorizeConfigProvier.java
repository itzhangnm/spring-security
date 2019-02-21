package com.zb.security.core.authorize;

import com.zb.security.core.properties.SecurityConstants;
import com.zb.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 默认权限配置
 *
 * @author zb
 * @date 2019/2/21 14:00
 */
@Component
public class DefaultAuthorizeConfigProvier implements AuthorizeConfigProvider {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry.antMatchers(SecurityConstants.DEFAULT_AUTHENTICATION_REQUIRE_URL,
				securityProperties.getBrowser().getSignInPage(),
				securityProperties.getSocial().getSignUpUrl(),
				SecurityConstants.DEFAULT_LOGIN_PROCESSOR_URL_MOBILE,
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
				securityProperties.getBrowser().getSession().getSessionInvalidUrl())
				.permitAll();
	}
}
