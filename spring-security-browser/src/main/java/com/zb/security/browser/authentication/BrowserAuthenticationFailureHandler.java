package com.zb.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.properties.LoginResponseType;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 *
 * @author zb
 * @date 2019/1/4 14:04
 */
@Component
@Slf4j
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private final ObjectMapper objectMapper;
	
	private final SecurityProperties securityProperties;
	
	
	public BrowserAuthenticationFailureHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
		this.objectMapper = objectMapper;
		this.securityProperties = securityProperties;
	}
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败");
		if (securityProperties.getBrowser().getSignInResponseType() == LoginResponseType.JSON){
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request,response,exception);
		}
	}
}
