package com.zb.security.core.social;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.connect.Connection;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * oauth 绑定/解绑 视图
 *
 * @author zb
 * @date 2019/1/29 17:37
 */
public class MyConnectView extends AbstractView {
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		List<Connection<?>> connections = (List<Connection<?>>) model.get("connections");
		if (CollectionUtils.isNotEmpty(connections)){
			response.getWriter().write("绑定成功");
		}else{
			response.getWriter().write("解绑成功");
		}
	}
}
