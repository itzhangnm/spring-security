package com.zb.security.core.validate.code;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 校验码处理接口
 *
 * @author zb
 * @date 2019/1/15 15:44
 */
public interface ValidateCodeProcessor extends ValidateCodeTypeStrategy {
	
	
	
	/**
	 * 创建校验码
	 * @param request
	 * @throws Exception
	 */
	void create (ServletWebRequest request) throws Exception;
	
	/**
	 * 校验验证码
	 * @param request
	 */
	void validate(ServletWebRequest request);
}
