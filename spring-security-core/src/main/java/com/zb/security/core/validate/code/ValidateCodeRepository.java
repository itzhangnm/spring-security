package com.zb.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码存储接口
 *
 * @author zb
 * @date 2019/2/15 10:40
 */
public interface ValidateCodeRepository {
	
	/**
	 * 保存验证码
	 * @param request
	 * @param code
	 * @param validateCodeType
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
	
	/**
	 * 获取验证码
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
	
	/**
	 * 移除验证码
	 * @param request
	 * @param validateCodeType
	 */
	void remove(ServletWebRequest request,ValidateCodeType validateCodeType);

}
