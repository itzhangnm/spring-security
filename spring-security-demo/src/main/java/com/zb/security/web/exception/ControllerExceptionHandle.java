package com.zb.security.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Controller} 异常处理
 *
 * @author zb
 * @date 2018/12/18 14:55
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandle {

//	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ControllerException.class)
	public ResponseEntity<Map<String,Object>> handleControllerException(ControllerException ce){
		Map<String,Object> map = new HashMap<>(2);
		map.put("code",ce.getCode());
		map.put("msg",ce.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
}
