package com.zb.security.service;

import com.zb.security.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * {@link User} {@link Service}
 *
 * @author zb
 * @date 2018/12/18 10:55
 */
public interface UserService {

	User findById(Long id);
	
}
