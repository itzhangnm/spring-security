package com.zb.security.app.authentication.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义JWT增强器
 *
 * @author zb
 * @date 2019/2/19 14:49
 */
public class MyJwtTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>(1);
		info.put("company", "zb");
		
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
}
