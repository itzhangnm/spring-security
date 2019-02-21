package com.zb.security.core.social.wechat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 获取微信用户实现
 *
 * @author zb
 * @date 2019/1/23 10:50
 */
@Slf4j
public class WeChatApiImpl extends AbstractOAuth2ApiBinding implements WeChatApi {
	
	private static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?openid=%s";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public WeChatApiImpl(String accessToken) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}
	
	@Override
	public WeChatUserInfo getUserInfo(String openId) {
		String url = String.format(GET_USER_INFO_URL,openId);
		String resultStr = getRestTemplate().getForObject(url, String.class);
		log.info(resultStr);
		WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
		try {
			weChatUserInfo = objectMapper.readValue(resultStr, WeChatUserInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return weChatUserInfo;
	}
	
}
