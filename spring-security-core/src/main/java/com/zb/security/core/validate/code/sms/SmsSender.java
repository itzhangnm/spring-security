package com.zb.security.core.validate.code.sms;

/**
 * 短信发送接口
 *
 * @author zb
 * @date 2019/1/15 17:00
 */
public interface SmsSender {
	
	/**
	 * 定义短信发送
	 * @param mobile
	 * @param code
	 */
	void send(String mobile,String code);
	
}
