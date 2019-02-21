//package com.zb.security.web.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * {@link WebMvcConfigurer} 自定义实现
// *
// * @author zb
// * @date 2018/12/18 17:03
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//
////	private final TimeInterceptor timeInterceptor;
////
////	@Autowired
////	public WebMvcConfig(TimeInterceptor timeInterceptor) {
////		this.timeInterceptor = timeInterceptor;
////	}
////
////	@Override
////	public void addInterceptors(InterceptorRegistry registry) {
////		registry.addInterceptor(timeInterceptor);
////	}
////
////	@Bean
////	public FilterRegistrationBean<TimeFilter> timeFilter(){
////		FilterRegistrationBean<TimeFilter> registrationBean = new FilterRegistrationBean<>();
////		registrationBean.setFilter(new TimeFilter());
////		registrationBean.addUrlPatterns("/*");
////		return registrationBean;
////	}
//}
