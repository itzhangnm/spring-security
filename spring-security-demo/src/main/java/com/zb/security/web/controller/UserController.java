package com.zb.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zb.security.core.properties.SecurityProperties;
import com.zb.security.model.dto.UserCondition;
import com.zb.security.model.entity.User;
import com.zb.security.validate.annotation.CheckUserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link } {@link Controller}
 *
 * @author zb
 * @date 2018/12/17 11:24
 */
@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;

//	@Autowired
//	private AppSignUpUtils appSignUpUtils;
	
	@Autowired
	private SecurityProperties securityProperties;

	@PostMapping("/register")
	public void register(User user, HttpServletRequest request){
		providerSignInUtils.doPostSignUp(user.getUserName(),new ServletWebRequest(request));
//		appSignUpUtils.doPostSignUp(user.getUserName(),new ServletWebRequest(request));
	}
	
	@GetMapping("/me")
	public Object getCurrentUser(Authentication user,HttpServletRequest request) {
		String token = StringUtils.substringAfter(request.getHeader("Authorization"),"Bearer").trim();
		try {
			Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getSigningKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();
			String company = (String) claims.get("company");
			System.out.println(company);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@GetMapping(value = "/list", name = "用户查询")
	@JsonView(User.UserSimpleView.class)
	public List<User> list(@Valid UserCondition userQuery, @PageableDefault(page = 2, size = 20, sort = "userName", direction = Sort.Direction.DESC) Pageable pageable) {
		System.out.println(ReflectionToStringBuilder.toString(userQuery, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(userQuery.getUserName());
		System.out.println(userQuery.getAge());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		List<User> users = new ArrayList<>(3);
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	
	@GetMapping(value = "/{id:\\d+}")
	@JsonView(User.UserInfoView.class)
	public User getInfo(@PathVariable(required = false) Long id) {
		System.out.println(id);
		User user = new User();
		user.setUserName("zb");
		user.setPassWord("123");
		user.setId(id);
		return user;
	}
	
	@PostMapping(value = "/add", name = "添加用户")
	public User add(@Valid @RequestBody UserCondition condition) {
		User user = new User();
		user.setUserName(condition.getUserName())
				.setAge(condition.getAge());
		return user;
	}
	
	@PutMapping(value = "/update/{id:\\d+}", name = "更新用户")
	public void update(@Valid @RequestBody UserCondition condition, @PathVariable Long id) {
		User user = new User();
		user.setId(id)
				.setAge(condition.getAge())
				.setUserName(condition.getUserName())
				.setBirthDay(condition.getBirthDay());
		System.out.println(user);
	}
	
	@DeleteMapping(value = "/delete/{id:\\d+}", name = "删除用户")
	public void delete(@PathVariable Long id) throws Exception {
		System.out.println(id);
//		throw new ControllerException(100,"删除异常!");
	}
	
	@GetMapping(value = "/check/{id}")
	public void check(@Length(max = 2,message = "最大长度2") @RequestParam String userName,
					  @CheckUserId @PathVariable Long id
					  ){
		
		System.out.println(userName);
		System.out.println(id);
	}
}

