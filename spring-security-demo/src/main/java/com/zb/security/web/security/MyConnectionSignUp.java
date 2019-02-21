package com.zb.security.web.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 自定义实现oauth signUp 处理
 *
 * @author zb
 * @date 2019/1/22 16:42
 */
//@Component
public class MyConnectionSignUp implements ConnectionSignUp {
	@Override
	public String execute(Connection<?> connection) {
		return connection.getDisplayName();
	}
}
