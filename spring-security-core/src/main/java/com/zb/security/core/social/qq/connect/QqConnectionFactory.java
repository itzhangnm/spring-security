package com.zb.security.core.social.qq.connect;

import com.zb.security.core.social.qq.api.QqApi;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 *
 * QQ 连接工厂
 * @author zb
 * @date 2019/1/18 16:30
 */
public class QqConnectionFactory extends OAuth2ConnectionFactory<QqApi> {
	
	public QqConnectionFactory(String providerId,String appId,String appSecret) {
		super(providerId, new QqServiceProvider(appId,appSecret), new QqApiAdapter());
	}
	
}
