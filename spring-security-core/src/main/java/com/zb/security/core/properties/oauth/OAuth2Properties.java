package com.zb.security.core.properties.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OAuth2 属性配置
 *
 * @author zb
 * @date 2019/2/18 18:04
 */
@Getter
@Setter
public class OAuth2Properties {
	private String tokenStore;
	private String signingKey = "zb";
	private OAuth2ClientProperties[] client = {};
	
}
