package com.zb.security.core.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 简单响应封装类
 *
 * @author zb
 * @date 2019/1/4 9:43
 */
@AllArgsConstructor
@Getter
@Setter
public class SimpleResponse {
	
	private Object content;
	
	public static SimpleResponse content(Object content){
		return new SimpleResponse(content);
	}
}
