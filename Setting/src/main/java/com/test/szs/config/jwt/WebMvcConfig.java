package com.test.szs.config.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final JwtTokenInterceptor jwtTokenInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("인터셉터 등록");
		registry.addInterceptor(jwtTokenInterceptor).addPathPatterns("/info");
	}
}