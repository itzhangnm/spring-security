package com.zb.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;

/**
 *
 * @author zb
 * @date 2019/2/21 14:45
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService{
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails){
			UserDetails userDetails = (UserDetails) principal;
			HashSet<String> hashSet = new HashSet<>();
			hashSet.add("/user/*");
			return hashSet.stream().anyMatch(url -> pathMatcher.match(url,request.getRequestURI()));
		}
		return false;
	}
}
