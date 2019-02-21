package com.zb.security.core.properties;

/**
 * security 常量
 *
 * @author zb
 * @date 2019/1/17 17:12
 */
public interface SecurityConstants {
	
	/**
	 * 默认校验码路径前缀
	 */
	String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
	
	/**
	 * 默认身份认证跳转路径
	 */
	String DEFAULT_AUTHENTICATION_REQUIRE_URL = "/authentication/require";
	
	/**
	 * 默认登陆页面
	 */
	String DEFAULT_LOGIN_PAGE_URL = "/default-signIn.html";
	
	/**
	 * 默认表单登陆认证路径
	 */
	String DEFAULT_LOGIN_PROCESSOR_URL_FORM = "/authentication/form";
	
	/**
	 * 默认手机登陆认证路径
	 */
	String DEFAULT_LOGIN_PROCESSOR_URL_MOBILE = "/authentication/mobile";
	
	/**
	 * 默认openId登陆认证路径
	 */
	String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
	
	/**
	 * 默认登出页面
	 */
	String DEFAULT_LOGOUT_PAGE_URL = "/default-signOut.html";
	
	/**
	 * 默认登出认证路径
	 */
	String DEFAULT_LOGOUT_URL = "/signOut";
	
	/**
	 * 默认图片验证码字段
	 */
	String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	
	/**
	 * 默认短信验证码字段
	 */
	String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	
	/**
	 * 默认短信请求字段
	 */
	String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
	
	/**
	 * openid参数名
	 */
	public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";
	
	/**
	 * providerId参数名
	 */
	public static final String DEFAULT_PARAMETER_NAME_PROVIDER_ID = "providerId";
	
	/**
	 * 默认记住我秒数
	 *
	 */
	int DEFAULT_REMEMBER_SECOND = 3600;
	
	/**
	 * 默认用户session最大在线数
	 */
	int DEFAULE_MAXIMUM_SESSIONS = 1;
	
	/**
	 * 默认用户session超过最大在线数，策略
	 */
	boolean DEFAULT_MAX_SESSIONS_PREVENTS_LOGIN = false;
	
	String DEFAULT_SESSION_INVALID_URL = "/default-session-invalid.html";
	
	/**
	 * 默认页面路径后缀
	 */
	String DEFAULT_URL_SUFFIX = ".html";
	
}
