package com.springboot.springbootmonitoring.annotation.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootmonitoring.annotation.TraceRequest;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TraceRequestAspect {
	private final Logger LOG = LoggerFactory.getLogger(TraceRequestAspect.class);
	public final HttpServletRequest request;
	private final ObjectMapper objectMapper;

	public TraceRequestAspect(HttpServletRequest request, ObjectMapper objectMapper) {
		this.request = request;
		this.objectMapper = objectMapper;
	}

	@Around("@annotation(com.springboot.springbootmonitoring.annotation.TraceRequest)")
	public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
		Map<String, String> headers = Collections.list(request.getHeaderNames())
				.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(String::toLowerCase, request::getHeader));

		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		TraceRequest annotation = method.getAnnotation(TraceRequest.class);
		
		headers.put("_label", annotation.label());
		headers.put("_tags", Arrays.toString(annotation.tags()));
		LOG.info("json {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headers));
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			LOG.error("label = {} tags = {} exception = {}", annotation.label(), annotation.tags(),
					e.getLocalizedMessage());
			throw e;
		}
	}

}