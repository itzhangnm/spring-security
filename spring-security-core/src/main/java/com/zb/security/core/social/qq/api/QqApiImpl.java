package com.zb.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.FNEG;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * QQ api 实现
 *
 * @author zb
 * @date 2019/1/18 15:44
 */
@Slf4j
public class QqApiImpl extends AbstractOAuth2ApiBinding implements QqApi {
	
	private final static String USER_INFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private final static String USER_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private String appId;
	private String openId;
	
	public QqApiImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		String result = getRestTemplate().getForObject(USER_OPEN_ID_URL, String.class);
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}
	
	@Override
	public QqUserInfo getUserInfo() {
		String url = String.format(USER_INFO_URL, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		log.info("QQ oauth获取用户:{}",result);
		QqUserInfo userInfo;
		try {
			userInfo = objectMapper.readValue(result,QqUserInfo.class);
			userInfo.setOpenId(appId);
		} catch (IOException e) {
			throw new RuntimeException("解析用户信息失败",e);
		}
		return userInfo;
	}
}
