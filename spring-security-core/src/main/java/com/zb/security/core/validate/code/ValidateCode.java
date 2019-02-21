package com.zb.security.core.validate.code;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抽象验证码类
 *
 * @author zb
 * @date 2019/1/15 14:56
 */
@Getter
public abstract class ValidateCode implements Serializable {
	
	private static final long serialVersionUID = 7059969953233377774L;
	protected String code;
	
	protected LocalDateTime expireTime;
	
	protected boolean expire;
	
	
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	/**
	 * 判断当前验证码是否过期
	 * @return
	 */
	protected boolean isExpire(){
		return this.expireTime.isBefore(LocalDateTime.now());
	}
}
