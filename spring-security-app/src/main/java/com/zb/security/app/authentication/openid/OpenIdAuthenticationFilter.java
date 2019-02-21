package com.zb.security.app.authentication.openid;

import com.zb.security.core.properties.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方openId 过滤器
 *
 * @author zb
 * @date 2019/2/15 17:15
 */

@Getter
@Setter
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
	private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDER_ID;
	private boolean postOnly = true;
	
	public OpenIdAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
	}
	
	public static void main(String[] args) {
		System.out.println(HttpMethod.POST.name());
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		String openid = obtainOpenId(request);
		String providerId = obtainProviderId(request);
		
		if (openid == null) {
			openid = "";
		}
		if (providerId == null) {
			providerId = "";
		}
		
		openid = openid.trim();
		providerId = providerId.trim();
		
		OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid, providerId);
		
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	/**
	 * 获取openId
	 */
	private String obtainOpenId(HttpServletRequest request) {
		return request.getParameter(openIdParameter);
	}
	
	/**
	 * 获取提供商id
	 */
	private String obtainProviderId(HttpServletRequest request) {
		return request.getParameter(providerIdParameter);
	}
	
	private void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
}
