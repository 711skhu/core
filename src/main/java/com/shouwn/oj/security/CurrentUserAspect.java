package com.shouwn.oj.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
public class CurrentUserAspect {

	@Pointcut("execution(* *(.., @CurrentUser (*), ..))")
	public void currentUser() {
	}

	@Around("currentUser()")
	public Object injectCurrentUser(ProceedingJoinPoint joinPoint) throws Throwable {

		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Annotation[][] annotations = method.getParameterAnnotations();

		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object[] args = joinPoint.getArgs();

		for (int i = 0, length = annotations.length; i < length; i++) {
			if (Arrays.stream(annotations[i]).anyMatch(this::isSupport)) {
				args[i] = memberId;
			}
		}

		return joinPoint.proceed(args);
	}

	private boolean isSupport(Annotation annotation) {
		return annotation.annotationType() == CurrentUser.class;
	}
}
