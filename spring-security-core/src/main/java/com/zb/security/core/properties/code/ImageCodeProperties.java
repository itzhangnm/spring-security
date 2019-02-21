package com.zb.security.core.properties.code;

import lombok.Getter;
import lombok.Setter;

/**
 * 图形验证码配置参数
 *
 * @author zb
 * @date 2019/1/10 15:41
 */
@Getter
@Setter
public class ImageCodeProperties extends AbstractCodeProperties {
	
	private int height = 23;
	
	private int width = 67;
	
	
}
