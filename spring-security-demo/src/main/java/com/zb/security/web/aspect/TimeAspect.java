package com.zb.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * TODO...
 *
 * @author zb
 * @date 2018/12/20 16:27
 */
//@Component
@Aspect
public class TimeAspect {
	
	@Pointcut("execution(* com.zb.security.web.controller.*.*(..))")
	public void around(){
	}
	
	@Around("around()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("TimeAspect start");
		long startTime = System.currentTimeMillis();
		Object object = joinPoint.proceed(joinPoint.getArgs());
		System.out.println("TimeAspect end");
		System.out.println("TimeAspect time consuming:" + (System.currentTimeMillis() - startTime));
		return object;
	}
}
