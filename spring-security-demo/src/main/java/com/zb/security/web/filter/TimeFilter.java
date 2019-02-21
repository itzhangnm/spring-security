package com.zb.security.web.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * {@link Filter} time log
 *
 * @author zb
 * @date 2018/12/18 16:53
 */
//@Component
public class TimeFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("timeFilter init");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		System.out.println("timeFilter start");
		filterChain.doFilter(servletRequest, servletResponse);
		long endTime = System.currentTimeMillis();
		System.out.println("timeFilter time consuming:" + (endTime - startTime));
	}
	
	@Override
	public void destroy() {
		System.out.println("timeFilter destroy");
	}
}
