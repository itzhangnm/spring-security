package com.zb.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author zb
 * @date 2019/2/19 17:29
 */
@EnableOAuth2Sso
@SpringBootApplication
@RestController
public class SsoClient2Application {
	
	@GetMapping("/user")
	public Authentication user(Authentication user){
		return user;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SsoClient2Application.class,args);
	}
}
