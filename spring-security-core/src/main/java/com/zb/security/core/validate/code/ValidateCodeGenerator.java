package com.zb.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 *
 * @author zb
 * @date 2019/1/10 16:12
 */
public interface ValidateCodeGenerator extends ValidateCodeTypeStrategy{
	
	
	
	/**
	 * 验证码生成接口
	 * @param request
	 * @return
	 */
	<T extends ValidateCode> T generate(ServletWebRequest request);
	
}
