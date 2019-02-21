package com.zb.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 认证服务器配置
 *
 * @author zb
 * @date 2019/2/19 17:30
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("zb")
				.secret(passwordEncoder.encode("zb"))
				.redirectUris("http://127.0.0.1:8081/client2/login")
				.accessTokenValiditySeconds(3600)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("all")
				.and()
				.withClient("dd")
				.secret(passwordEncoder.encode("dd"))
				.redirectUris("http://127.0.0.1:8080/client1/login")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("all");
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).accessTokenConverter(jwtTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("myKeys");
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtTokenConverter());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
