package com.zb.security.core.validate.code.image;

import com.zb.security.core.validate.code.AbstractValidateCodeProcessor;
import com.zb.security.core.validate.code.ValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import com.zb.security.core.validate.code.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;

/**
 * 图片验证码处理器
 *
 * @author zb
 * @date 2019/1/15 16:56
 */
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
	
	
	public ImageCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeRepository validateCodeRepository) {
		super(validateCodeGenerators, ValidateCodeType.IMAGE, validateCodeRepository);
	}
	
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
		ImageIO.write(imageCode.getImage(),"JPEG",request.getResponse().getOutputStream());
	}
}
