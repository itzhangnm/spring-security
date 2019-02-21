package com.zb.security.core.support;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 第三方用户基础信息
 *
 * @author zb
 * @date 2019/1/22 15:23
 */
@Getter
@Setter
@Accessors(chain = true)
public class OAuthUserInfo {
	
	private String providerId;
	
	private String providerUserId;
	
	private String nickName;
	
	private String headImg;

}
