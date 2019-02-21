package com.zb.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session strategy 抽象
 *
 * @author zb
 * @date 2019/1/30 14:45
 */
@Slf4j
public abstract class AbstractSessionStrategy {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private boolean createNewSession = true;
	
	private final String sessionInvalidUrl;
	
	private final String suffix;
	
	public AbstractSessionStrategy(String sessionInvalidUrl, String suffix) {
		this.suffix = suffix;
		Assert.isTrue(UrlUtils.isValidRedirectUrl(sessionInvalidUrl), "url must start with '/' or with 'http(s)'");
		Assert.isTrue(StringUtils.endsWithIgnoreCase(sessionInvalidUrl,suffix),"url must end with '"+suffix+"'");
		this.sessionInvalidUrl = sessionInvalidUrl;
	}
	
	public void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (createNewSession){
			request.getSession();
		}
		String sourceUrl = request.getRequestURI();
		if (StringUtils.endsWithIgnoreCase(sourceUrl,suffix)){
			log.info("session失效，引发跳转的路径:{}",sourceUrl);
			redirectStrategy.sendRedirect(request,response,sessionInvalidUrl);
		}else{
			log.info("session失效！");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			Object result = buildResponseContent(request);
			response.getWriter().write(objectMapper.writeValueAsString(result));
		}
	}
	
	/**
	 *
	 * @param request
	 * @return
	 */
	protected Object buildResponseContent(HttpServletRequest request){
		String content = "session过期!";
		if (isConcurrent()){
			content += ",有可能是并发登陆导致的!";
		}
		return SimpleResponse.content(content);
	}
	
	/**
	 * 是否是并发操作
	 * @return
	 */
	protected boolean isConcurrent(){
		return false;
	}
	
	/**
	 *
	 * 重新生成sessionId,避免循环重定向
	 *
	 * @param createNewSession defaults to {@code true}.
	 */
	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}
	
}
