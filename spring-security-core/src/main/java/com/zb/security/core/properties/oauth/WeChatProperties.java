package com.zb.security.core.properties.oauth;

import com.zb.security.core.properties.oauth.SocialProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信属性配置
 *
 * @author zb
 * @date 2019/1/23 13:57
 */
@Getter
@Setter
public class WeChatProperties extends SocialProperties {
	
	private String providerId;
}
