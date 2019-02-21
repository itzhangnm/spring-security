package com.zb.security.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 自定义 {@link UserDetailsService} 实现
 *
 * @author zb
 * @date 2019/1/3 10:48
 */
@Slf4j
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService,SocialUserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		String passWord = passwordEncoder.encode("123456");
		log.info("用户登陆,用户名:{},密码:{}",userName,passWord);
		return new User(userName,passWord, true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String mobile) throws UsernameNotFoundException {
		String passWord = passwordEncoder.encode("123456");
		log.info("用户登陆,用户名:{},密码:{}",mobile,passWord);
		return new SocialUser(mobile,passWord,true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
	}
	
	
}
