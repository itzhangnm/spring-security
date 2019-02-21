package com.zb.security.core.validate.code;

import com.zb.security.core.properties.SecurityConstants;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码校验控制器
 *
 * @author zb
 * @date 2019/1/10 11:08
 */
@RestController
public class ValidateCodeController {
	
	private final ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	public ValidateCodeController(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
		this.validateCodeProcessorHolder = validateCodeProcessorHolder;
	}
	
	@GetMapping(value = SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}", name = "请求验证码")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
		validateCodeProcessorHolder.findProcessorByType(type).create(new ServletWebRequest(request,response));
	}
}
