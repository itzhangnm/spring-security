package com.zb.security.core.social;

import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * 属性配置
 *
 * @author zb
 * @date 2019/1/21 17:56
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {
	
	private final String processesUrl;
	
	private final String signInUp;
	
	private SocialAuthenticationFilterPostProcessor postProcessor;
	
	public MySpringSocialConfigurer(String processesUrl, String signInUp) {
		this.processesUrl = processesUrl;
		this.signInUp = signInUp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter t = (SocialAuthenticationFilter)super.postProcess(object);
		t.setFilterProcessesUrl(processesUrl);
		t.setSignupUrl(signInUp);
		if (null != postProcessor){
			postProcessor.process(t);
		}
		return (T) t;
	}
	
	public void setPostProcessor(SocialAuthenticationFilterPostProcessor postProcessor) {
		this.postProcessor = postProcessor;
	}
}
