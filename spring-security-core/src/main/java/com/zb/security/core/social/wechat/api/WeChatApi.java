package com.zb.security.core.social.wechat.api;

/**
 * 微信接口
 *
 * @author zb
 * @date 2019/1/23 10:49
 */
public interface WeChatApi {
	
	/**
	 * 获取微信用户实体内容
	 * @param openId
	 * @return
	 */
	WeChatUserInfo getUserInfo(String openId);

}
