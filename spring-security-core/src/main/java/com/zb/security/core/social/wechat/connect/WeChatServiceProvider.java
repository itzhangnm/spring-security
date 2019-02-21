package com.zb.security.core.social.wechat.connect;

import com.zb.security.core.social.wechat.api.WeChatApi;
import com.zb.security.core.social.wechat.api.WeChatApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信 oauth2 服务提供者
 *
 * @author zb
 * @date 2019/1/23 10:59
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChatApi> {
	
	private final static String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
	private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	WeChatServiceProvider(String appId,String appSecret) {
		super(new WeChatTemplate(appId,appSecret,AUTHORIZE_URL,ACCESS_TOKEN_URL));
	}
	
	
	@Override
	public WeChatApi getApi(String accessToken) {
		return new WeChatApiImpl(accessToken);
	}
}
