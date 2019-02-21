package com.zb.security.app.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * TODO...
 *
 * @author zb
 * @date 2019/2/18 15:58
 */
@Component
public class AppSignUpUtils {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	
	/**
	 * 保存用户第三方信息
	 *
	 * @param request
	 * @param connectionData
	 */
	@SuppressWarnings("unchecked")
	public void save(WebRequest request, ConnectionData connectionData) {
		redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
	}
	
	/**
	 * 注册用户
	 * @param userId
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public void doPostSignUp(String userId, WebRequest request){
		String key = getKey(request);
		if (!redisTemplate.hasKey(key)){
			throw new AppSecretException("用户信息不存在!");
		}
		ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
		Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
		redisTemplate.delete(key);
	}
	
	
	private String getKey(WebRequest request) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new AppSecretException("设备Id不存在!");
		}
		return "my:security:social:connect" + deviceId;
	}
	
	
}
