package com.zb.security.core.social.wechat.connect;

import com.zb.security.core.social.wechat.api.WeChatApi;
import com.zb.security.core.social.wechat.api.WeChatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 oauth 适配器
 *
 * @author zb
 * @date 2019/1/23 10:55
 */
public class WeChatApiAdapter implements ApiAdapter<WeChatApi> {
	
	private final String openId;
	
	public WeChatApiAdapter(String openId) {
		this.openId = openId;
	}
	
	@Override
	public boolean test(WeChatApi api) {
		return false;
	}
	
	@Override
	public void setConnectionValues(WeChatApi api, ConnectionValues values) {
		WeChatUserInfo userInfo = api.getUserInfo(openId);
		values.setProviderUserId(userInfo.getOpenid());
		values.setDisplayName(userInfo.getNickname());
		values.setProfileUrl(null);
		values.setImageUrl(userInfo.getHeadimgurl());
	}
	
	@Override
	public UserProfile fetchUserProfile(WeChatApi api) {
		return null;
	}
	
	@Override
	public void updateStatus(WeChatApi api, String message) {
	
	}
}
