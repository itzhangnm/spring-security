package com.zb.security.core.validate.code;

import com.zb.security.core.properties.SecurityConstants;

/**
 * 校验码类型
 *
 * @author zb
 * @date 2019/1/17 17:09
 */
public enum ValidateCodeType {
	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};
	
	public abstract String getParamNameOnValidate();
}
