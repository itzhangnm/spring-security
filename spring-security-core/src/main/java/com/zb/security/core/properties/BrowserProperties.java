package com.zb.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * 浏览器环境配置
 * @author zb
 * @date 2019/1/4 10:11
 */

@Getter
@Setter
public class BrowserProperties {
	
	/**
	 * 默认登陆页
	 */
	private String signInPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	/**
	 * 默认登出页
	 */
	private String signOutPage = SecurityConstants.DEFAULT_LOGOUT_PAGE_URL;
	
	/**
	 * 登出认证页
	 */
	private String signOutUrl = SecurityConstants.DEFAULT_LOGOUT_URL;
	
	/**
	 * 登陆页后缀
	 */
	private String signInSufFix = SecurityConstants.DEFAULT_URL_SUFFIX;
	
	/**
	 * 登陆返回类型
	 */
	private LoginResponseType signInResponseType = LoginResponseType.JSON;
	
	/**
	 * 记住我时间
	 */
	private int rememberSecond = SecurityConstants.DEFAULT_REMEMBER_SECOND;
	
	private SessionProperties session = new SessionProperties();
	
	
}
