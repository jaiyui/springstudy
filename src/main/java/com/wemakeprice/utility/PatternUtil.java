package com.wemakeprice.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

	/**
	 * 패턴 매칭
	 * @param sourceMessage
	 * @param replaceMessage
	 * @param pattern
	 * @return String
	 */
	public static String messageReplace ( String sourceMessage, String replaceMessage, String pattern ){
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(sourceMessage);
		replaceMessage = replaceMessage.replaceAll("\\$","\\\\\\$");
		String targetValue = m.replaceAll(replaceMessage);
		//String targetValue = Matcher.quoteReplacement (replaceMessage);
		return targetValue;
	}

	/**
	 * 첫번째 문자열만 패턴 매칭: 대소문자 구분없음
	 * @param sourceMessage
	 * @param replaceMessage
	 * @param pattern
	 * @return String
	 */
	public static String messageReplaceFirst ( String sourceMessage, String replaceMessage, String pattern ){
		
		if((sourceMessage.toUpperCase()).startsWith(pattern)) {
			Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(sourceMessage);
			return m.replaceFirst(replaceMessage);
		}

		return sourceMessage;
	}

}
