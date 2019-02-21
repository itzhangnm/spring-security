package com.zb.security.app;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.properties.oauth.OAuth2ClientProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;
import java.util.List;

/**
 * oauth 认证服务器配置
 *
 * @author zb
 * @date 2019/2/13 13:54
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtTokenConverter;
	
	@Autowired(required = false)
	private TokenEnhancer jwtTokenEnhancer;
	
	public MyAuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
		if (jwtTokenConverter != null && jwtTokenEnhancer != null) {
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancerList = Arrays.asList(jwtTokenEnhancer,jwtTokenConverter);
			enhancerChain.setTokenEnhancers(enhancerList);
			endpoints.accessTokenConverter(jwtTokenConverter)
					.tokenEnhancer(enhancerChain);
		}
		
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		OAuth2ClientProperties[] client = securityProperties.getOauth2().getClient();
		if (ArrayUtils.isNotEmpty(client)) {
			Arrays.stream(client).forEach(config -> config(builder, config));
		}
	}
	
	private void config(InMemoryClientDetailsServiceBuilder builder, OAuth2ClientProperties config) {
		builder.withClient(config.getClientId())
				.secret(passwordEncoder.encode(config.getSecret()))
				.accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
				.refreshTokenValiditySeconds(Integer.MAX_VALUE)
				.authorizedGrantTypes("password", "refresh_token", "authorization_code")
				.scopes("all", "xxx");
	}
}
