package com.zb.security.core.validate.code.sms;


import lombok.extern.slf4j.Slf4j;

/**
 * 默认短信发送
 *
 * @author zb
 * @date 2019/1/15 17:01
 */
@Slf4j
public class DefaultSmsSender implements SmsSender {
	
	@Override
	public void send(String mobile,String code) {
		log.info("手机号:{},短信发送成功,验证码:{}",mobile,code);
	}
}
