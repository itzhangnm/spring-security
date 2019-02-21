package com.zb.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.properties.LoginResponseType;
import com.zb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private final ObjectMapper objectMapper;
	
	private final SecurityProperties securityProperties;
	
	
	public AppAuthenticationFailureHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
		this.objectMapper = objectMapper;
		this.securityProperties = securityProperties;
	}
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败");
		if (securityProperties.getBrowser().getSignInResponseType() == LoginResponseType.JSON){
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
		}else{
			super.onAuthenticationFailure(request,response,exception);
		}
	}
}
