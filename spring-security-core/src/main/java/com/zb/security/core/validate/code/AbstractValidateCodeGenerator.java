package com.zb.security.core.validate.code;

import lombok.Getter;

/**
 * 校验码生成器抽象
 *
 * @author zb
 * @date 2019/1/18 11:47
 */
@Getter
public abstract class AbstractValidateCodeGenerator implements ValidateCodeGenerator {
	
	protected final ValidateCodeType validateCodeType;
	
	protected AbstractValidateCodeGenerator(ValidateCodeType validateCodeType) {
		this.validateCodeType = validateCodeType;
	}
}
