package com.zb.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * session 属性配置
 *
 * @author zb
 * @date 2019/1/30 14:53
 */
@Getter
@Setter
public class SessionProperties {
	
	private int maximumSessions = SecurityConstants.DEFAULE_MAXIMUM_SESSIONS;
	private boolean maxSessionsPreventsLogin = SecurityConstants.DEFAULT_MAX_SESSIONS_PREVENTS_LOGIN;
	private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
	private String urlSuffix = SecurityConstants.DEFAULT_URL_SUFFIX;
	
}
