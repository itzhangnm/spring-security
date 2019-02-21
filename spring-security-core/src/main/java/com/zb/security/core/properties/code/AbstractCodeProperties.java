package com.zb.security.core.properties.code;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * 抽象验证码类
 * @author zb
 * @date 2019/1/15 14:50
 */
@Getter
@Setter
abstract class AbstractCodeProperties {
	
	protected int length = 4;
	
	protected int expireIn = 60;
	
	private String url;
}
