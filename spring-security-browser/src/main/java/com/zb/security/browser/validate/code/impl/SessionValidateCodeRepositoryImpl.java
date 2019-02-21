package com.zb.security.browser.validate.code.impl;

import com.zb.security.core.validate.code.ValidateCode;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import com.zb.security.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * session存储验证码实现
 *
 * @author zb
 * @date 2019/2/15 11:02
 */
public class SessionValidateCodeRepositoryImpl implements ValidateCodeRepository {
	
	/**
	 * session工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		sessionStrategy.setAttribute(request,getSessionKey(validateCodeType),code);
	}
	
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
		return (ValidateCode) sessionStrategy.getAttribute(request,getSessionKey(validateCodeType));
	}
	
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
		sessionStrategy.removeAttribute(request,getSessionKey(validateCodeType));
	}
	
	/**
	 * 构建验证码放入session时的key
	 * @return
	 */
	private String getSessionKey(ValidateCodeType validateCodeType) {
		String sessionKeyPrefix = "SESSION_KEY_FOR_CODE_";
		return sessionKeyPrefix + validateCodeType.toString().toUpperCase();
	}
}
