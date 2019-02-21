package com.zb.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

import javax.annotation.processing.Processor;

/**
 * socialAuthenticationFilter 后置处理实现
 *
 * @author zb
 * @date 2019/2/18 10:38
 */
public interface SocialAuthenticationFilterPostProcessor {
	
	/**
	 * socialAuthenticationFilter 后置处理实现
	 *
	 * @param socialAuthenticationFilter
	 */
	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
