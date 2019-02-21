package com.zb.security.core.properties.oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * social 配置属性
 *
 * @author zb
 * @date 2019/1/18 17:28
 */
@Getter
@Setter
public class SecuritySocialProperties {
	private String processesUrl = "/qqLogin";
	private String signUpUrl = "/default-signInUp.html";
	private QqProperties qq = new QqProperties();
	private WeChatProperties weChat = new WeChatProperties();
}
