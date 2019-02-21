package com.zb.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Security Study 启动类
 *
 * @author zb
 * @date 2018/12/14 11:37
 */
@SpringBootApplication
@RestController
public class SecurityApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	
	
	@GetMapping(value = "/hello", name = "Hello测试")
	public String hello() {
		return "hello security";
	}
	
}
