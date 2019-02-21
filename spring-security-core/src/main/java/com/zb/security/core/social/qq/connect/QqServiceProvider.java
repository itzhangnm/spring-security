package com.zb.security.core.social.qq.connect;

import com.zb.security.core.social.qq.api.QqApi;
import com.zb.security.core.social.qq.api.QqApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *
 * QQ认证服务提供者
 * @author zb
 * @date 2019/1/18 16:03
 */
public class QqServiceProvider extends AbstractOAuth2ServiceProvider<QqApi> {
	
	private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
	
	private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
	
	private String appId;
	
	QqServiceProvider(String appId,String appSecret) {
		super(new QqTemplate(appId,appSecret,AUTHORIZE_URL,ACCESS_TOKEN_URL));
		this.appId = appId;
	}
	
	@Override
	public QqApi getApi(String accessToken) {
		return new QqApiImpl(accessToken,appId);
	}
}
