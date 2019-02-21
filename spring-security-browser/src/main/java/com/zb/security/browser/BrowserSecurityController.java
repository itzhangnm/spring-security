package com.zb.security.browser;

import com.zb.security.core.support.OAuthUserInfo;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Browser 安全认证
 *
 * @author zb
 * @date 2019/1/4 9:41
 */
@Slf4j
@RestController
public class BrowserSecurityController {
	
	private final SecurityProperties securityProperties;
	
	private final ProviderSignInUtils providerSignInUtils;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public BrowserSecurityController(SecurityProperties securityProperties, ProviderSignInUtils providerSignInUtils) {
		this.securityProperties = securityProperties;
		this.providerSignInUtils = providerSignInUtils;
	}
	
	
	@RequestMapping(value = "/authentication/require",name = "身份认证跳转")
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null){
			String redirectUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的url:{}",redirectUrl);
			if (StringUtils.endsWithIgnoreCase(redirectUrl,securityProperties.getBrowser().getSignInSufFix())){
				redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getSignInPage());
			}
		}
		return SimpleResponse.content("访问资源需要身份认证,请引导用户到登陆页!");
	}
	
	@GetMapping(value = "/oauth/user",name = "获取第三方用户信息")
	public OAuthUserInfo getOauthUser(HttpServletRequest request){
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		if (connection != null){
			OAuthUserInfo userInfo = new OAuthUserInfo();
			userInfo.setProviderId(connection.getKey().getProviderId())
					.setProviderUserId(connection.getKey().getProviderUserId())
					.setNickName(connection.getDisplayName())
					.setHeadImg(connection.getImageUrl());
			return userInfo;
		}
		return null;
	}
	
}
