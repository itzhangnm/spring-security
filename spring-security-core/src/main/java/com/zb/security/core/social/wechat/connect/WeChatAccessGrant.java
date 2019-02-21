package com.zb.security.core.social.wechat.connect;

import lombok.Getter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信自定义请求token返回实体
 *
 * @author zb
 * @date 2019/1/23 16:40
 */
@Getter
public class WeChatAccessGrant extends AccessGrant {
	
	private final String openId;
	private final String unionId;
	
	
	public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openId, String unionId) {
		super(accessToken, scope, refreshToken, expiresIn);
		this.openId = openId;
		this.unionId = unionId;
	}
}
