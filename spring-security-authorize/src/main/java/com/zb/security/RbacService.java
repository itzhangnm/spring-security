package com.zb.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * rbac接口定义
 * @author zb
 * @date 2019/2/21 14:42
 */
public interface RbacService {
	
	/**
	 * 判断是否有权限
	 * @param request
	 * @param authentication
	 * @return
	 */
	boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
