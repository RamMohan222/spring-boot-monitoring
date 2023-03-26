package com.springboot.springbootmonitoring.annotation.aspect;

import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootmonitoring.model.TokenInfo;

public class TokenInfoResolver implements HandlerMethodArgumentResolver {

	private final ObjectMapper objectMapper;

	public TokenInfoResolver(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(TokenInfo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		String jsonContext = nativeWebRequest.getHeader("X-App-Context");
		if (jsonContext != null) {
			return objectMapper.readValue(jsonContext, TokenInfo.class);
		}
		String name = nativeWebRequest.getParameter("useranme");
		return new TokenInfo(name != null ? name : "x-username", new Date().getTime());
	}
}