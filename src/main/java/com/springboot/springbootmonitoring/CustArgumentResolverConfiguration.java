package com.springboot.springbootmonitoring;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootmonitoring.annotation.aspect.SessionIdResolver;
import com.springboot.springbootmonitoring.annotation.aspect.TokenInfoResolver;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustArgumentResolverConfiguration implements WebMvcConfigurer {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new SessionIdResolver());
		resolvers.add(new TokenInfoResolver(objectMapper));
	}

}