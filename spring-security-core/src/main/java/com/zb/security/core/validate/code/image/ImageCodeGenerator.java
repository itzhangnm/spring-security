package com.zb.security.core.validate.code.image;

import com.zb.security.core.properties.code.ImageCodeProperties;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.core.validate.code.AbstractValidateCodeGenerator;
import com.zb.security.core.validate.code.ValidateCodeType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图形验证码生成器
 *
 * @author zb
 * @date 2019/1/10 16:14
 */
public class ImageCodeGenerator extends AbstractValidateCodeGenerator {
	
	private final SecurityProperties securityProperties;
	
	public ImageCodeGenerator(SecurityProperties securityProperties) {
		super(ValidateCodeType.IMAGE);
		this.securityProperties = securityProperties;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ImageCode generate(ServletWebRequest request) {
		ImageCodeProperties properties = securityProperties.getCode().getImage();
		int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",properties.getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",properties.getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		
		Random random = new Random();
		
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		StringBuilder sRand = new StringBuilder();
		for (int i = 0; i < properties.getLength(); i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		
		g.dispose();
		
		return new ImageCode(image, sRand.toString(), properties.getExpireIn());
	}
	
	/**
	 * 生成随机背景条纹
	 *
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
