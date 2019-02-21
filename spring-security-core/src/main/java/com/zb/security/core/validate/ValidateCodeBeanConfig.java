package com.zb.security.core.validate;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.code.ValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeProcessor;
import com.zb.security.core.validate.code.ValidateCodeProcessorHolder;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import com.zb.security.core.validate.code.image.ImageCodeGenerator;
import com.zb.security.core.validate.code.image.ImageCodeProcessor;
import com.zb.security.core.validate.code.sms.DefaultSmsSender;
import com.zb.security.core.validate.code.sms.SmsCodeGenerator;
import com.zb.security.core.validate.code.sms.SmsCodeProcessor;
import com.zb.security.core.validate.code.sms.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 校验Bean 配置
 *
 * @author zb
 * @date 2019/1/10 16:15
 */
@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private Map<String,ValidateCodeGenerator> validateCodeGenerators;
	
	@Autowired
	private Map<String,ValidateCodeProcessor> validateCodeProcessors;
	
	
	private final ValidateCodeRepository validateCodeRepository;
	
	private final SecurityProperties securityProperties;
	
	
	public ValidateCodeBeanConfig(ValidateCodeRepository validateCodeRepository, SecurityProperties securityProperties) {
		this.validateCodeRepository = validateCodeRepository;
		this.securityProperties = securityProperties;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator(){
		return new ImageCodeGenerator(securityProperties);
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
	public ValidateCodeGenerator smsCodeGenerator(){
		return new SmsCodeGenerator(securityProperties);
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsSender.class)
	public SmsSender smsSender(){
		return new DefaultSmsSender();
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "smsValidateCodeProcessor")
	public ValidateCodeProcessor smsCodeProcessor(){
		return new SmsCodeProcessor(validateCodeGenerators,smsSender(), validateCodeRepository);
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeProcessor")
	public ValidateCodeProcessor imageCodeProcessor(){
		return new ImageCodeProcessor(validateCodeGenerators, validateCodeRepository);
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "validateCodeProcessorHolder")
	public ValidateCodeProcessorHolder validateCodeProcessorHolder(){
		return new ValidateCodeProcessorHolder(validateCodeProcessors);
	}
}
