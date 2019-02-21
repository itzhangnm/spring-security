package com.zb.security.core.social.qq.connect;

import com.zb.security.core.social.qq.api.QqApi;
import com.zb.security.core.social.qq.api.QqUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * qq api 适配器
 *
 * @author zb
 * @date 2019/1/18 16:26
 */
public class QqApiAdapter implements ApiAdapter<QqApi> {
	@Override
	public boolean test(QqApi api) {
		return true;
	}
	
	@Override
	public void setConnectionValues(QqApi api, ConnectionValues values) {
		QqUserInfo userInfo = api.getUserInfo();
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		values.setProfileUrl(null);
		values.setProviderUserId(userInfo.getOpenId());
	}
	
	@Override
	public UserProfile fetchUserProfile(QqApi api) {
		return null;
	}
	
	@Override
	public void updateStatus(QqApi api, String message) {
	
	}
}
