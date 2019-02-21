package com.zb.security.browser;

import com.zb.security.core.authentication.AbstractSecurityChannelConfig;
import com.zb.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zb.security.core.authorize.AuthorizeConfigManager;
import com.zb.security.core.properties.SecurityConstants;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.security.Security;

/**
 * Browser {@link Security} {@link Configuration}
 *
 * @author zb
 * @date 2019/1/2 17:06
 */
@Configuration
public class BrowserSecurityConfiguration extends AbstractSecurityChannelConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeConfig;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	@Autowired
	private FindByIndexNameSessionRepository<? extends Session> sessionRepository;
	
	public BrowserSecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler,SecurityProperties securityProperties) {
		super(authenticationSuccessHandler, authenticationFailureHandler, securityProperties);
	}
	
	
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		tokenRepository.setCreateTableOnStartup(false);
		return tokenRepository;
	}
	
	@Bean
	public SpringSessionBackedSessionRegistry sessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(sessionRepository);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		applyPasswordAuthenticationConfig(http);
		http.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.apply(validateCodeConfig)
				.and()
			.apply(springSocialConfigurer)
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberSecond())
				.userDetailsService(userDetailsService)
				.and()
			.sessionManagement()
				.invalidSessionStrategy(invalidSessionStrategy)
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
				.expiredSessionStrategy(sessionInformationExpiredStrategy)
				.sessionRegistry(sessionRegistry())
				.and()
				.and()
			.logout()
				.logoutUrl(securityProperties.getBrowser().getSignOutUrl())
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
			.csrf()
				.disable();
		authorizeConfigManager.config(http.authorizeRequests());
	}
	
}
