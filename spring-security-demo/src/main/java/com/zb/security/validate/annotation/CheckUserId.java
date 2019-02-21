package com.zb.security.validate.annotation;

import com.zb.security.validate.validatar.CheckUserIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * 校验用户ID
 *
 * @author zb
 * @date 2018/12/18 10:46
 */
@Documented
@Target({METHOD,FIELD,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUserIdValidator.class)
public @interface CheckUserId {
	
	String message() default "用户ID不合法";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
}
