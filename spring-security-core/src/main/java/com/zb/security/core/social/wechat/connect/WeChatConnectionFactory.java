package com.zb.security.core.social.wechat.connect;

import com.zb.security.core.social.wechat.api.WeChatApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 *
 * @author zb
 * @date 2019/1/23 10:56
 */
public class WeChatConnectionFactory extends OAuth2ConnectionFactory<WeChatApi> {
	
	
	public WeChatConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new WeChatServiceProvider(appId, appSecret), null);
	}
	
	
	@Override
	public Connection<WeChatApi> createConnection(AccessGrant accessGrant) {
		return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
	}
	
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if (accessGrant instanceof WeChatAccessGrant) {
			return ((WeChatAccessGrant) accessGrant).getOpenId();
		}
		return super.extractProviderUserId(accessGrant);
	}
	
	private ApiAdapter<WeChatApi> getApiAdapter(String openId) {
		return new WeChatApiAdapter(openId);
	}
	
	private OAuth2ServiceProvider<WeChatApi> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<WeChatApi>) getServiceProvider();
	}
}
