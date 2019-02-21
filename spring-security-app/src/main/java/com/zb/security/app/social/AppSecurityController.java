package com.zb.security.app.social;

import com.zb.security.core.support.OAuthUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * App social 认证跳转
 *
 * @author zb
 * @date 2019/2/18 16:13
 */
@RestController
public class AppSecurityController {
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@Autowired
	private AppSignUpUtils appSignUpUtils;
	
	@GetMapping(value = "/social/signUp",name = "获取第三方用户信息")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public OAuthUserInfo getOauthUser(HttpServletRequest request){
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		if (connection != null){
			OAuthUserInfo userInfo = new OAuthUserInfo();
			userInfo.setProviderId(connection.getKey().getProviderId())
					.setProviderUserId(connection.getKey().getProviderUserId())
					.setNickName(connection.getDisplayName())
					.setHeadImg(connection.getImageUrl());
			appSignUpUtils.save(new ServletWebRequest(request),connection.createData());
			
			return userInfo;
		}
		return null;
	}
}
