package com.zb.security.app.social;

/**
 * app登陆异常
 *
 * @author zb
 * @date 2019/2/18 16:12
 */
public class AppSecretException extends RuntimeException {
	
	public AppSecretException(String message) {
		super(message);
	}
}
