package com.zb.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 权限配置工作
 *
 * @author zb
 * @date 2019/2/21 13:57
 */
public interface AuthorizeConfigManager {
	
	/**
	 * 权限配置
	 * @param registry
	 */
	void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry);
}
