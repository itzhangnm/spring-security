package com.zb.security.browser;

import com.zb.security.browser.logout.MyLogoutSuccessHandler;
import com.zb.security.browser.session.MyExpiredSessionStrategy;
import com.zb.security.browser.session.MyInvalidSessionStrategy;
import com.zb.security.browser.validate.code.impl.SessionValidateCodeRepositoryImpl;
import com.zb.security.core.properties.BrowserProperties;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.properties.SessionProperties;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * browser ben 配置
 *
 * @author zb
 * @date 2019/1/30 14:44
 */
@Configuration
public class BrowserSecurityBeanConfig {
	
	private final SessionProperties sessionConfig;
	
	private final BrowserProperties browserConfig;
	
	public BrowserSecurityBeanConfig(SecurityProperties securityProperties) {
		this.sessionConfig = securityProperties.getBrowser().getSession();
		this.browserConfig = securityProperties.getBrowser();
	}
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new MyExpiredSessionStrategy(sessionConfig.getSessionInvalidUrl(),sessionConfig.getUrlSuffix());
	}
	
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new MyInvalidSessionStrategy(sessionConfig.getSessionInvalidUrl(),sessionConfig.getUrlSuffix());
	}
	
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new MyLogoutSuccessHandler(browserConfig);
	}
	
	@Bean
	@ConditionalOnMissingBean(ValidateCodeRepository.class)
	public ValidateCodeRepository validateCodeRepository(){
		return new SessionValidateCodeRepositoryImpl();
	}
}
