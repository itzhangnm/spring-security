package com.zb.security.web.exception;

import org.springframework.stereotype.Controller;

/**
 * {@link Controller} 层异常定义
 *
 * @author zb
 * @date 2018/12/18 14:27
 */
public class ControllerException extends RuntimeException {
	
	
	private static final long serialVersionUID = 679256733794695076L;
	
	private Integer code;
	
	public ControllerException(Integer code,String message) {
		super(message);
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
}
