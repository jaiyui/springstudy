package com.wemakeprice.utility;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LogUtil {
 
	/**
	 * 호출 시 입력 정보 로깅
	 * @param logger
	 * @param className
	 * @param method
	 * @param text
	 */
	public static void infoCall(Logger logger, String className, String method, String text) {
		try {
			logger.info("-------Call Start--------" + DateUtil.formatTodayWithMil());
			logger.info(" - [" + className + ": " + method + "]");
			logger.info(" - parameters -");
			logger.info(text + "");
			logger.info("-------Call End--------\n");
		} catch (Exception e) {
		}
	}

	/**
	 * 반환 시 출력 정보 로깅
	 * @param logger
	 * @param className
	 * @param method
	 * @param text
	 */
	public static void infoReturn(Logger logger, String className, String method, String text) {
		try {
			logger.info("-------Return Start--------" + DateUtil.formatTodayWithMil());
			logger.info(" - [" + className + ": " + method + "]");
			logger.info(" - values -");
			logger.info(text + "");
			logger.info("-------Return End--------\n");
		} catch (Exception e) {
		}
	}

	/**
	 * 예외 발생 시 로깅 
	 * @param logger
	 * @param className
	 * @param method
	 * @param error
	 */
	public static void infoThrow(Logger logger, String className, String method, Throwable error) {
		try {
			logger.info("-------Throw Start--------" + DateUtil.formatTodayWithMil());
			logger.info(" - [" + className + ": " + method + "]");
			logger.info(" - values -");
			logger.info("   Error: " + error + "");
			logger.info(getStackTraces(error));
			logger.info("-------Throw End--------\n");
		} catch (Exception e) {
		}
	}

	/**
	 * 개별 로깅 시 사용
	 * @param logger
	 * @param text
	 */
	public static void infoText(Logger logger, String text) {
		
		try {
			
/*   로그파일 Dao 제거			
 			logger.info("-------Text Start--------" + DateUtil.formatTodayWithMil());
			logger.info(text + "");
			logger.info("-------Text End--------\n");
*/
		} catch (Exception e) {
		}
	}

	/**
	 * session id
	 * @param logger
	 * @param text
	 */
	public static void infoSessionId(Logger logger, HttpSession session) {
		try {
			if(session != null) {
				logger.info("\n-- session id: " + session.getId());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 개별 로깅 시 사용: debug
	 * @param logger
	 * @param text
	 */
	public static void debugText(Logger logger, String text) {
		try {
			logger.debug("---------------Text Start------------------" + DateUtil.formatTodayWithMil());
			logger.debug(text + "");
			logger.debug("---------------Text End------------------\n");
		} catch (Exception e) {
		}
	}

	/**
	 * PrintStackTrace 로깅
	 * @param logger
	 * @param error
	 */
	public static void infoPrintStackTrace(Logger logger, Throwable error) {
		try {
			logger.info("-------PrintStackTrace Start--------" + DateUtil.formatTodayWithMil());
			logger.info(getStackTraces(error));
			logger.info("-------PrintStackTrace End--------\n");
		} catch (Exception e) {
		}
	}
	
	/**
	 * PrintStackTrace 로깅
	 * @param error
	 * @return
	 */
	private static String getStackTraces(Throwable error) {
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("error message: " + error.getMessage() + "");
			
			int line = 0;
			StackTraceElement[] stackTraces = error.getStackTrace();
			for(StackTraceElement stackTrace : stackTraces) {
				sb.append("     " + stackTrace.getClassName() + ": " + stackTrace.getFileName() + " (" + stackTrace.getLineNumber() + ") \n");
				line++;
				
				if(line > 10) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
