package com.zb.security.core.social.qq.config;

import com.zb.security.core.properties.oauth.QqProperties;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.social.AbstractAutoConfigurerAdapter;
import com.zb.security.core.social.qq.api.QqApi;
import com.zb.security.core.social.qq.connect.QqConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author zb
 * @date 2019/1/18 16:33
 */
@Configuration
@ConditionalOnProperty(prefix = "my.security.social.qq", name = {"appId", "appSecret"})
public class QqAutoConfig extends AbstractAutoConfigurerAdapter<QqApi> {
	
	private final SecurityProperties securityProperties;
	
	public QqAutoConfig(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	@Override
	public ConnectionFactory<QqApi> createConnectionFactory() {
		QqProperties qqConfig = securityProperties.getSocial().getQq();
		return new QqConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}
	
	
}
