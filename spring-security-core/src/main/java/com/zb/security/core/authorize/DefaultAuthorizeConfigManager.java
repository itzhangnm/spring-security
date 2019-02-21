package com.zb.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认实现
 *
 * @author zb
 * @date 2019/2/21 14:02
 */
@Component
public class DefaultAuthorizeConfigManager implements AuthorizeConfigManager {
	
	@Autowired
	private List<AuthorizeConfigProvider> list;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		list.forEach(provider -> provider.config(registry));
		registry.anyRequest().authenticated();
	}
}
