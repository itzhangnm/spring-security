package com.zb.security.core.social;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.properties.oauth.SecuritySocialProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.*;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;
import java.util.List;


/**
 * Social 配置
 *
 * @author zb
 * @date 2019/1/18 17:14
 */
@Configuration
@EnableSocial
@Order(10)
public class SocialSecurityConfig extends SocialConfigurerAdapter{
	
	private final List<ConnectInterceptor<?>> connectInterceptors;
	
	private final List<DisconnectInterceptor<?>> disconnectInterceptors;
	
	private final List<ProviderSignInInterceptor<?>> signInInterceptors;
	

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;
	
	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;
	
	public SocialSecurityConfig(ObjectProvider<List<ConnectInterceptor<?>>> connectInterceptorsProvider,
								ObjectProvider<List<DisconnectInterceptor<?>>> disconnectInterceptorsProvider,
								ObjectProvider<List<ProviderSignInInterceptor<?>>> signInInterceptorsProvider) {
		this.connectInterceptors = connectInterceptorsProvider.getIfAvailable();
		this.disconnectInterceptors = disconnectInterceptorsProvider.getIfAvailable();
		this.signInInterceptors = signInInterceptorsProvider.getIfAvailable();
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(connectionSignUp);
		return repository;
	}

	@Bean
	public SpringSocialConfigurer springSocialConfigurer(){
		SecuritySocialProperties social = securityProperties.getSocial();
		MySpringSocialConfigurer springSocialConfigurer = new MySpringSocialConfigurer(social.getProcessesUrl(), social.getSignUpUrl());
		springSocialConfigurer.setPostProcessor(socialAuthenticationFilterPostProcessor);
		return springSocialConfigurer;
	}
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
		return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
	}
	
	@Bean(name = "/connect/status")
	@ConditionalOnMissingBean(name = "/connect/status")
	public View connectStatusView(){
		return new MyConnectStatusView();
	}
	
	@Bean
	@ConditionalOnMissingBean(ConnectController.class)
	public ConnectController connectController(
			ConnectionFactoryLocator factoryLocator,
			ConnectionRepository repository) {
		ConnectController controller = new ConnectController(factoryLocator,
				repository);
		if (!CollectionUtils.isEmpty(this.connectInterceptors)) {
			controller.setConnectInterceptors(this.connectInterceptors);
		}
		if (!CollectionUtils.isEmpty(this.disconnectInterceptors)) {
			controller.setDisconnectInterceptors(this.disconnectInterceptors);
		}
		return controller;
	}
}
