package com.springboot.springbootmonitoring.annotation.aspect;

import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.springboot.springbootmonitoring.annotation.SessionId;

public class SessionIdResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(SessionId.class) != null && parameter.getParameterType().equals(UUID.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//String requestPath = ((ServletWebRequest) webRequest).getRequest().getPathInfo();
		SessionId annotation = parameter.getParameterAnnotation(SessionId.class);
		String param = annotation.param();
		if(param == null) {
			param = parameter.getParameterName();
		}

		return  webRequest.getParameter(param);
	}
}