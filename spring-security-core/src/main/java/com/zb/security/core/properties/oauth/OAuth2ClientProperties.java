package com.zb.security.core.properties.oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 连接 配置属性
 *
 * @author zb
 * @date 2019/2/18 18:05
 */
@Getter
@Setter
public class OAuth2ClientProperties {
	
	private String clientId;
	
	private String secret;
	
	private int accessTokenValiditySeconds;
	
}
