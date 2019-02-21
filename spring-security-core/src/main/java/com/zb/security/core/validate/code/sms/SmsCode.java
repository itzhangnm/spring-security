package com.zb.security.core.validate.code.sms;

import com.zb.security.core.validate.code.ValidateCode;

import java.time.LocalDateTime;

/**
 * 短信验证码实体
 *
 * @author zb
 * @date 2019/1/15 15:05
 */
public class SmsCode extends ValidateCode {
	
	private static final long serialVersionUID = 5164563542667811369L;
	
	public SmsCode(String code, LocalDateTime expireTime) {
		super(code, expireTime);
	}
	
	public SmsCode(String code,int expireInt){
		super(code, LocalDateTime.now().plusSeconds(expireInt));
	}
}
