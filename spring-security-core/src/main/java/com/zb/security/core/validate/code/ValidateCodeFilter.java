package com.zb.security.core.validate.code;

import com.zb.security.core.properties.SecurityConstants;
import com.zb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 验证码校验过滤器
 *
 * @author zb
 * @date 2019/1/10 11:21
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	private final SecurityProperties securityProperties;
	
	private final ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	
	public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties, ValidateCodeProcessorHolder validateCodeProcessorHolder) {
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.securityProperties = securityProperties;
		this.validateCodeProcessorHolder = validateCodeProcessorHolder;
	}
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSOR_URL_FORM, ValidateCodeType.IMAGE);
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSOR_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(ValidateCodeType.IMAGE, securityProperties.getCode().getImage().getUrl());
		addUrlToMap(ValidateCodeType.SMS, securityProperties.getCode().getSms().getUrl());
	}
	
	/**
	 * 添加系统配置需要校验的url
	 *
	 * @param codeType
	 * @param url
	 */
	private void addUrlToMap(ValidateCodeType codeType, String url) {
		String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
		if (ArrayUtils.isNotEmpty(urls)) {
			for (String u : urls) {
				urlMap.put(u, codeType);
			}
		}
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		boolean isMatch = urlMap.keySet().stream().anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
		if (isMatch) {
			try {
				ValidateCodeType codeType = urlMap.get(request.getRequestURI());
				log.info("路径:{},校验验证码类型:{}", request.getRequestURI(), codeType.toString());
				validateCodeProcessorHolder.findProcessorByType(codeType).validate(new ServletWebRequest(request, response));
				log.info("{},校验成功",codeType);
			} catch (ValidateCodeException e) {
				log.info("路径:{},校验验证码异常", request.getRequestURI());
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
