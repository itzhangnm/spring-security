package com.zb.security.app;

import com.zb.security.app.authentication.jwt.MyJwtTokenEnhancer;
import com.zb.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * token存储策略配置
 *
 * @author zb
 * @date 2019/2/18 18:02
 */
@Configuration
public class TokenStoreConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnProperty(prefix = "my.security.oauth2.",name = "tokenStore",havingValue = "redis")
	public TokenStore tokenStore(){
		return new MyRedisTokenStore(redisConnectionFactory);
	}
	
	@Configuration
	@ConditionalOnProperty(prefix = "my.security.oauth2.",name = "tokenStore",havingValue = "jwt",matchIfMissing = true)
	public class JwtTokenStoreConfig{
		
		@Bean
		public TokenStore tokenStore(){
			return new JwtTokenStore(jwtAccessTokenConverter());
		}
		
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter(){
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey(securityProperties.getOauth2().getSigningKey());
			return converter;
		}
		
		@Bean
		@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer(){
			return new MyJwtTokenEnhancer();
		}
	}
}
