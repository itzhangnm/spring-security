package com.zb.security.core.properties.oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * social 配置属性
 *
 * @author zb
 * @date 2019/1/18 17:30
 */
@Getter
@Setter
public abstract class SocialProperties {
	
	private String appId;
	
	private String appSecret;
}
