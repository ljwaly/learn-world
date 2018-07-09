package com.learn.word.aop;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAop {

	private static Queue<String> miguQueue = new LinkedBlockingQueue<String>();
	
	@Pointcut("within(com.learn.word.controller..*)")
	public void requestController() {

	}

	@AfterThrowing(value = "requestController()", throwing = "ex")
	public void doThrowing(JoinPoint joinPoint, Throwable ex) {

		System.out.println("ljw.doThrowing");
		System.out.println(ex);
	}

	
	
	@Around("requestController()")
	public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
		
		
		Map<String, Object> map = null;
		Object proceed = joinPoint.proceed();//执行过程
		if (proceed instanceof java.util.Map) {
			map = (Map<String, Object>) proceed;

			map.put("extend", "111");
		}

		return map;

	}

}
