package com.zb.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.support.SimpleResponse;
import com.zb.security.core.properties.BrowserProperties;
import com.zb.security.core.properties.LoginResponseType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出处理器
 *
 * @author zb
 * @date 2019/2/1 17:02
 */
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	private final BrowserProperties browserProperties;
	
	public MyLogoutSuccessHandler(BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}
	
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (browserProperties.getSignInResponseType() == LoginResponseType.JSON){
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("登出成功")));
		}else{
			super.onLogoutSuccess(request, response, authentication);
		}
	}
}
