package com.wemakeprice.utility;

import java.util.ArrayList;
import java.util.List;


public class NullCheckUtil {

	/**
	 * 모든 Object의 Null를 체크한다.
	 * @param objects
	 * @return 
	 */
	public static boolean isNull(Object[] objects) {
		if(objects != null && objects.length > 0) {
			for(Object object : objects) {
				
				if(object instanceof String) {
					if((String) object == null || "".equals((String) object) || "NULL".equals( ((String) object).toUpperCase() )) {
						return true;
					}
				} else if(object instanceof Integer) {
					if((Integer) object == 0) {
						return true;
					}
				} else if(object instanceof Long) {
					if((Long) object == 0) {
						return true;
					}
				} else if(object instanceof Double) {
					if((Double) object == 0.0) {
						return true;
					}
				} else if(object instanceof String[]) {
					if((String[]) object == null || ((String[]) object).length <= 0) {
						return true;
					}
				} else if(object instanceof int[]) {
					if((int[]) object == null || ((int[]) object).length <= 0) {
						return true;
					}
				} else if(object instanceof List) {
					if(object == null || ((ArrayList<?>) object).size() <= 0) {
						return true;
					}
				} else {
					if(object == null) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * 배열의 Null/length를 체크한다.
	 * @param objects
	 * @return
	 */
	public static boolean isNulltoArray(Object[] objects) {
		if(objects == null || objects.length <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * List의 Null/size를 체크한다. 
	 * @param object
	 * @return
	 */
	public static boolean isNulltoList(List<?> object) {
		if(object == null || object.size() <= 0) {
			return true;
		}
		return false;
	}
}

