package com.wemakeprice.common.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.google.gson.Gson;
import com.wemakeprice.common.exception.NullDataException;
import com.wemakeprice.common.exception.ParameterException;
import com.wemakeprice.common.message.MessageSetter;
import com.wemakeprice.utility.LogUtil;

@Aspect
public class RequestIntercepter {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 사용자용 인증 service
	 */
	@Resource(name = "MessageSetter")
	private MessageSetter messageSetter;

	/**
	 * 모든 Exception에 대한 pointcut
	 */
	@Pointcut("execution(* com.wemakeprice..*Controller*.*(..))")
	public void pointcut4throwing() {}

	/**
	 * 모든 controller에 대한 pointcut
	 */
	@Pointcut("execution(* com.wemakeprice..*Controller*.*(..))")
	public void call4Controller() {}


	/**
	 * 모든 controller의 호출에 대한 return log
	 * @param joinPoint
	 */
	@AfterReturning("call4Controller()")
	public void _after(JoinPoint joinPoint) {
		
		HttpSession session = null;
		Logger _logger = null;


		String device = "";
		Object[] args = joinPoint.getArgs();
		
		try {

			for (Object arg : args) {
				if(arg instanceof HttpSession) {
					session = (HttpSession) arg;
					_logger = logger;
					break;
				}
			}

		} catch (Exception e) {
			LogUtil.infoPrintStackTrace(logger, e);
		} finally {
			args = null;
		}
	}

	/**
	 * 모든 호출에 대한 exception
	 * @param joinPoint
	 * @param error
	 * @throws Exception
	 */
	@AfterThrowing(pointcut = "pointcut4throwing()", throwing = "error")
	public void _throwing(JoinPoint joinPoint, Throwable error) throws Exception {
		Logger _logger = null;
		Logger _logger_error = null;
		String device = "";
		String jsonView = "jsonViewer";

		Object[] args = joinPoint.getArgs();
		
		try {
			
			for (Object arg : args) {
				if(arg instanceof HttpSession) {
					
					jsonView = "jsonViewer";
					_logger = logger;
					_logger_error = logger;
					break;
				}
			}
		} catch (Exception e) {
			LogUtil.infoPrintStackTrace(logger, e);
		} finally {
			args = null;
		}

		Map<String, ?> map = null;
		
		if(error instanceof NullDataException) {
			map = messageSetter.setModelWithMessageEx("1000", "NullDataException");
			
		} else if(error instanceof ParameterException) {
			map = messageSetter.setModelWithMessageEx("1001", "ParameterException");
			
		} else {
			
			map = messageSetter.setException(error.getMessage());
			if(map == null) {
					map = messageSetter.setModelWithMessageEx("2000", error.getMessage());
					LogUtil.infoThrow(_logger_error, joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), error);
			}
		}
		
		LogUtil.infoReturn(_logger, joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), this.getReturn(map));
		throw new ModelAndViewDefiningException(new ModelAndView(jsonView, map)); 
	}
	

	
	/**
	 * return parsing : 리턴데이터를 로그에 남기기 위함
	 * @param map
	 * @return
	 */
	private String getReturn(Map<?, ?> map) {
		
		StringBuffer sb = new StringBuffer();
		Entry<?, ?> entry;
		Set<?> set;
		Iterator<?> it;
		Object value = null;
		String val = null;
		int len = 1000;
		
		try {
			set = map.entrySet();
			it = set.iterator();
			
			while(it.hasNext()) {
				entry = (Entry<?, ?>) it.next();
				value = entry.getValue();

				if(value instanceof String) {
					if(((String) value).length() > len) {
						value = ((String) value).substring(0, len);
					}
					sb.append("   " + entry.getKey() + ": " + value + "\n");
				} else {
					val = new Gson().toJson(value);
					
					if(val.length() > len) {
						val = val.substring(0, len);
					}
					sb.append("   class: "
							+ value.getClass()
							+ "\n"
							+ "   "
							+ val);
				}
			}
			
			
			
		} catch(Exception e) {
			LogUtil.infoPrintStackTrace(logger, e);
		} finally {
			entry = null;
			set = null;
		}
		return sb.toString();
	}

	

}