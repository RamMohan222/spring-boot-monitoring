package com.springboot.springbootmonitoring.annotation.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springboot.springbootmonitoring.annotation.TraceException;

@Aspect
@Component
public class TraceExceptionAspect {

	private Logger LOG = LoggerFactory.getLogger(TraceExceptionAspect.class);

	@Around("@annotation(com.springboot.springbootmonitoring.annotation.TraceException)")
	public Object exeception(ProceedingJoinPoint joinPoint) throws Throwable {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		TraceException trace = method.getAnnotation(TraceException.class);
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			LOG.error("Label = {} tags = {} Exception {}", trace.value(), trace.tags(), e.getLocalizedMessage());
			throw e;
		}
	}
}
