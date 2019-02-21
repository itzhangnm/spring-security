package com.zb.security.core.social.wechat.config;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.properties.oauth.WeChatProperties;
import com.zb.security.core.social.AbstractAutoConfigurerAdapter;
import com.zb.security.core.social.MyConnectView;
import com.zb.security.core.social.wechat.connect.WeChatConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信 oauth 配置
 *
 * @author zb
 * @date 2019/1/23 11:24
 */
@Configuration
@ConditionalOnProperty(prefix = "my.security.social.weChat",name = {"appId","appSecret"})
public class WeChatAutoConfig extends AbstractAutoConfigurerAdapter {
	
	
	private final SecurityProperties securityProperties;
	
	public WeChatAutoConfig(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeChatProperties weChatConfig = securityProperties.getSocial().getWeChat();
		return new WeChatConnectionFactory(weChatConfig.getProviderId(),weChatConfig.getAppId(),weChatConfig.getAppSecret());
	}
	
	@Bean({"connect/weixinConnected","connect/weixinConnect"})
	@ConditionalOnMissingBean(name = "weChatConnectedView")
	public View weChatConnectedView(){
		return new MyConnectView();
	}
	
}
