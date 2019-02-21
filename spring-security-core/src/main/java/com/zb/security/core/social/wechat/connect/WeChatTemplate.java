package com.zb.security.core.social.wechat.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 微信oauth 模板
 *
 * @author zb
 * @date 2019/1/23 11:00
 */
@Slf4j
public class WeChatTemplate extends OAuth2Template {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private String appId;
	private String appSecret;
	private String accessTokenUrl;
	
	public WeChatTemplate(String appId, String appSecret, String authorizeUrl, String accessTokenUrl) {
		super(appId, appSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
		this.appId = appId;
		this.appSecret = appSecret;
		this.accessTokenUrl = accessTokenUrl;
	}
	
	@Override
	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		String url = super.buildAuthenticateUrl(parameters);
		url = url.replace("client_id", "appid");
		url = url + "&scope=snsapi_login";
		return url;
	}
	
	@Override
	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
		return buildAuthenticateUrl(parameters);
	}
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
	
	@Override
	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("appid", appId);
		params.set("secret", appSecret);
		params.set("code", authorizationCode);
		params.set("grant_type", "authorization_code");
		params.set("redirect_uri", redirectUri);
		return postForAccessGrant(accessTokenUrl, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String restStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		Map<String, Object> result = null;
		try {
			result = objectMapper.readValue(restStr, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))) {
			throw new RestClientException("weChat postForAccessGrant error:" + restStr);
		}
		return new WeChatAccessGrant(MapUtils.getString(result, "access_token"),
									 MapUtils.getString(result, "scope"),
									 MapUtils.getString(result, "refresh_token"),
									 MapUtils.getLong(result, "expires_in"),
									 MapUtils.getString(result, "openid"),
									 MapUtils.getString(result, "unionid"));
	}
}
