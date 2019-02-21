package com.zb.security.core.validate.code;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 验证码处理器封装
 *
 * @author zb
 * @date 2019/1/17 16:58
 */
@Slf4j
public class ValidateCodeProcessorHolder {
	
	private final Map<String, ValidateCodeProcessor> validateCodeProcessors;
	
	public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
		this.validateCodeProcessors = validateCodeProcessors;
	}
	
	public ValidateCodeProcessor findProcessorByType(String type) {
		try {
			return findProcessorByType(ValidateCodeType.valueOf(type.toUpperCase()));
		} catch (IllegalArgumentException e) {
			log.error("验证码类型:{}不存在", type);
			throw new ValidateCodeException("验证码类型" + type + "不存在");
		}
	}
	
	
	public ValidateCodeProcessor findProcessorByType(ValidateCodeType type) {
		return validateCodeProcessors.values().stream()
				.filter(isProcessor(type)).findFirst()
				.orElseThrow(() ->new ValidateCodeException("验证码类型" + type.toString() + "处理器不存在"));
	}
	
	private Predicate<ValidateCodeProcessor> isProcessor(ValidateCodeType type) {
		return process -> process.getValidateCodeType().equals(type);
	}
	
	
}
