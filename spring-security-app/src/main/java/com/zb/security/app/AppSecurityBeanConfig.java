package com.zb.security.app;

import com.zb.security.app.social.MySocialAuthenticationFilterPostProcessorImpl;
import com.zb.security.app.validate.code.impl.RedisValidateCodeRepositoryImpl;
import com.zb.security.core.social.SocialAuthenticationFilterPostProcessor;
import com.zb.security.core.validate.ValidateCodeBeanConfig;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * app bean 配置类
 *
 * @author zb
 * @date 2019/2/15 11:24
 */
@Configuration
public class AppSecurityBeanConfig {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@SuppressWarnings("unchecked")
	@Bean
	@ConditionalOnMissingBean(ValidateCodeRepository.class)
	public ValidateCodeRepository validateCodeRepository(){
		return new RedisValidateCodeRepositoryImpl(redisTemplate);
	}
	
	@Bean
	@ConditionalOnMissingBean(SocialAuthenticationFilterPostProcessor .class)
	public SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessorImpl(){
		return new MySocialAuthenticationFilterPostProcessorImpl(successHandler);
	}
}
