package com.zb.security.core.authentication.mobile;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信验证码 Filter
 *
 * @author zb
 * @date 2019/1/16 11:19
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	public static final String SECURITY_FORM_MOBILE_KEY = "mobile";
	
	private String mobileParameter = SECURITY_FORM_MOBILE_KEY;
	
	private boolean postOnly = true;
	
	public SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
	}
	
	public static void main(String[] args) {
		System.out.println(HttpMethod.POST.name());
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		String mobile = obtainMobile(request);
		
		if (mobile == null) {
			mobile = "";
		}
		
		mobile = mobile.trim();
		
		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
		
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}
	
	
	protected void setDetails(HttpServletRequest request,
							  SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
	
	
	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}
	
	
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	
	public final String getMobileParameter() {
		return mobileParameter;
	}
	
	
}
