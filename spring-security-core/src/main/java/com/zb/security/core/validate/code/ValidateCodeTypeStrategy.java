package com.zb.security.core.validate.code;

/**
 * 验证码类型 策略
 *
 * @author zb
 * @date 2019/1/18 13:44
 */
public interface ValidateCodeTypeStrategy {
	
	/**
	 * 获取校验码类型
	 * @return ValidateCodeType
	 */
	ValidateCodeType getValidateCodeType();
}
