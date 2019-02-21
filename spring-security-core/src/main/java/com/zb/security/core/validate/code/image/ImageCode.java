package com.zb.security.core.validate.code.image;

import com.zb.security.core.validate.code.ValidateCode;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码实体
 *
 * @author zb
 * @date 2019/1/10 11:12
 */
@Getter
public class ImageCode extends ValidateCode {
	
	private static final long serialVersionUID = -1877730418419266340L;
	
	
	private transient BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireDateTime) {
		super(code,expireDateTime);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, int expireInt) {
		this(image,code,LocalDateTime.now().plusSeconds(expireInt));
	}
	
}
