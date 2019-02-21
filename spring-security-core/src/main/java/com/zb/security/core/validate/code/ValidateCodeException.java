package com.zb.security.core.validate.code;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常实体
 *
 * @author zb
 * @date 2019/1/10 11:24
 */
public class ValidateCodeException extends AuthenticationException {
	
	private static final long serialVersionUID = 4133430437580032902L;
	
	public ValidateCodeException(String msg) {
		super(msg);
	}
}
