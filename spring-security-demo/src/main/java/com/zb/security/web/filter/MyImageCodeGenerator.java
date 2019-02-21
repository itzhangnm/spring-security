package com.zb.security.web.filter;

import com.zb.security.core.validate.code.AbstractValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeType;
import com.zb.security.core.validate.code.image.ImageCode;
import com.zb.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 自定义图形验证码生成器
 *
 * @author zb
 * @date 2019/1/10 16:19
 */
public class MyImageCodeGenerator extends AbstractValidateCodeGenerator {
	
	public MyImageCodeGenerator() {
		super(ValidateCodeType.IMAGE);
	}
	
	@Override
	public ImageCode generate(ServletWebRequest request) {
		System.out.println("更高级图形验证码!");
		return null;
	}
}
