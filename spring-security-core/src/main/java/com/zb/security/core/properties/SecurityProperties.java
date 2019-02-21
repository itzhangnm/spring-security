package com.zb.security.core.properties;

import com.zb.security.core.properties.code.ValidateCodeProperties;
import com.zb.security.core.properties.oauth.OAuth2Properties;
import com.zb.security.core.properties.oauth.SecuritySocialProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全环境配置
 *
 * @author zb
 * @date 2019/1/4 10:09
 */
@Getter
@ConfigurationProperties(prefix = "my.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	private SecuritySocialProperties social = new SecuritySocialProperties();
	
	private OAuth2Properties oauth2 = new OAuth2Properties();
}
