package com.zb.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author zb
 * @date 2019/2/19 17:23
 */
@SpringBootApplication
@RestController
public class SsoServerApplication {
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class,args);
	}
}
