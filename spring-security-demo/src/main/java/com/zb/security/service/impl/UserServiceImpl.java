package com.zb.security.service.impl;

import com.zb.security.model.entity.User;
import com.zb.security.service.UserService;
import org.springframework.stereotype.Service;

/**
 * {@link User} {@link Service} 实现
 *
 * @author zb
 * @date 2018/12/18 10:55
 */
@Service
public class UserServiceImpl implements UserService {
	@Override
	public User findById(Long id) {
		return id < 0 ? null : new User();
	}
}
