package com.zb.security.core.validate.code.sms;

import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.code.AbstractValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeType;
import com.zb.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 *
 * @author zb
 * @date 2019/1/15 15:18
 */
public class SmsCodeGenerator extends AbstractValidateCodeGenerator {
	
	
	private final SecurityProperties securityProperties;
	
	public SmsCodeGenerator(SecurityProperties securityProperties) {
		super(ValidateCodeType.SMS);
		this.securityProperties = securityProperties;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SmsCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new SmsCode(code,securityProperties.getCode().getSms().getExpireIn());
	}
}
