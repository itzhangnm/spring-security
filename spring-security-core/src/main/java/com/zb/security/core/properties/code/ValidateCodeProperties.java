package com.zb.security.core.properties.code;

import lombok.Getter;

/**
 * 验证码配置
 *
 * @author zb
 * @date 2019/1/10 15:40
 */
@Getter
public class ValidateCodeProperties {
	
	private ImageCodeProperties image = new ImageCodeProperties();
	
	private SmsCodeProperties sms = new SmsCodeProperties();
}
