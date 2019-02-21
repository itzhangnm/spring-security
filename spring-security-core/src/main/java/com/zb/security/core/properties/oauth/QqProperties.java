package com.zb.security.core.properties.oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * qq 配置
 *
 * @author zb
 * @date 2019/1/18 17:29
 */
@Getter
@Setter
public class QqProperties extends SocialProperties {
	private String providerId = "callback.do";
}
