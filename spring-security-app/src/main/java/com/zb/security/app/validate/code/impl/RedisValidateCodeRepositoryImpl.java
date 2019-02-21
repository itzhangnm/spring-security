package com.zb.security.app.validate.code.impl;

import com.zb.security.core.validate.code.ValidateCode;
import com.zb.security.core.validate.code.ValidateCodeException;
import com.zb.security.core.validate.code.ValidateCodeRepository;
import com.zb.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * redis存储验证码实现
 *
 * @author zb
 * @date 2019/2/15 11:17
 */
public class RedisValidateCodeRepositoryImpl implements ValidateCodeRepository {
	
	private final RedisTemplate<String,ValidateCode> redisTemplate;
	
	public RedisValidateCodeRepositoryImpl(RedisTemplate<String, ValidateCode> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType) {
		redisTemplate.opsForValue().set(buildKey(request,codeType),code,30, TimeUnit.MINUTES);
	}
	
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
		return redisTemplate.opsForValue().get(buildKey(request,codeType));
	}
	
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		redisTemplate.delete(buildKey(request,codeType));
	}
	
	private String buildKey(ServletWebRequest request, ValidateCodeType codeType) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("请在请求头中携带deviceId参数");
		}
		return "code:" + codeType.toString().toLowerCase() + ":" + deviceId;
	}
}
