package com.zb.security.core.validate.code.sms;

import com.zb.security.core.validate.code.AbstractValidateCodeProcessor;
import com.zb.security.core.validate.code.ValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import com.zb.security.core.validate.code.ValidateCodeType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 短信验证码处理器
 *
 * @author zb
 * @date 2019/1/15 16:58
 */
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {
	
	private final SmsSender smsSender;
	
	
	public SmsCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, SmsSender smsSender, ValidateCodeRepository validateCodeRepository) {
		super(validateCodeGenerators, ValidateCodeType.SMS, validateCodeRepository);
		this.smsSender = smsSender;
	}
	
	
	@Override
	protected void send(ServletWebRequest request, SmsCode smsCode) throws ServletRequestBindingException {
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		smsSender.send(mobile, smsCode.getCode());
	}
}
