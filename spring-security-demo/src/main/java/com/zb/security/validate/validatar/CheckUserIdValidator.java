package com.zb.security.validate.validatar;

import com.zb.security.service.UserService;
import com.zb.security.validate.annotation.CheckUserId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户ID校验器
 *
 * @author zb
 * @date 2018/12/18 10:50
 */
public class CheckUserIdValidator implements ConstraintValidator<CheckUserId,Long> {
	
	private final UserService userService;
	
	public CheckUserIdValidator(UserService userService) {
		this.userService = userService;
	}
	
	
	@Override
	public void initialize(CheckUserId checkUserId) {
	
	}
	
	@Override
	public boolean isValid(Long userId, ConstraintValidatorContext context) {
		System.out.println(userId);
		return userService.findById(userId) != null;
	}
}
