package com.learn.word.interrupt;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import scala.annotation.bridge;


@Component
public class MyHandlerInterceptor implements HandlerInterceptor{

	
	/**
	 * 前置方法
	 * preHandle返回为true，继续执行，false，进行拦截
	 * 进入控制器方法之前运行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("before");
		return true;
	}

	
	/**
	 * 后置方法
	 * 控制器方法执行结束，DispatcherServlet执行结果匹配ModelAndView之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
		System.out.println("after");
		
	}

	/**
	 * 完成方法
	 * DispatcherServlet执行结果匹配modelandview之后执行
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("buzhidao ");
		
	}

}
