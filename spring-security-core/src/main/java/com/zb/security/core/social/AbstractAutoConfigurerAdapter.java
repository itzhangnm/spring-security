package com.zb.security.core.social;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * oauth 配置器 抽象
 *
 * @author zb
 * @date 2019/1/23 11:26
 */
public abstract class AbstractAutoConfigurerAdapter<A> extends SocialConfigurerAdapter{
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
		configurer.addConnectionFactory(createConnectionFactory());
	}
	@Override
	public UserIdSource getUserIdSource() {
		return ()-> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
			}
			return authentication.getName();
		};
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return null;
	}
	
	/**
	 * 创建连接工厂
	 * @return
	 */
	protected abstract ConnectionFactory<A> createConnectionFactory();
	
}
