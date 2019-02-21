package com.zb.security.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * QQ 模板请求
 *
 * @author zb
 * @date 2019/1/21 18:16
 */
@Slf4j
public class QqTemplate extends OAuth2Template {
	
	QqTemplate(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String resultStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		log.info("QQ oauth2 响应:{}", resultStr);
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(resultStr, "&");
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expireTime = Long.valueOf(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		return new AccessGrant(accessToken, null, refreshToken, expireTime);
		
	}
	
}
