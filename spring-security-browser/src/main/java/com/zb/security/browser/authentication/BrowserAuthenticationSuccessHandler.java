package com.zb.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.properties.LoginResponseType;
import com.zb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 *
 * @author zb
 * @date 2019/1/4 14:02
 */
@Component
@Slf4j
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	
	private final ObjectMapper objectMapper;

	private final SecurityProperties securityProperties;

	public BrowserAuthenticationSuccessHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
		this.objectMapper = objectMapper;
		this.securityProperties = securityProperties;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("登录成功");
		if (securityProperties.getBrowser().getSignInResponseType() == LoginResponseType.JSON){
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(objectMapper.writeValueAsString(authentication));
		}else{
			super.onAuthenticationSuccess(request,response,authentication);
		}
	}
}
