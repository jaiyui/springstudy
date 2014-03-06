package com.wemakeprice.utility;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
public class ParseStringUtil {


	/**
	 * 한글을 원하는 사이즈로 자르는 유팅
	 * @param raw
	 * @param len
	 * @param encoding ( UTF-8, EUC-KR )
	 * @return String
	 */
	public static String parseStringByBytes( String raw, int len, String encoding ) {  
		
		  if (raw == null) return null;
		  String[] ary = null;
		  try {
		   // raw 의 byte
		   byte[] rawBytes = raw.getBytes(encoding);
		   int rawLength = rawBytes.length;
		   
		   int index = 0;
		   int minus_byte_num = 0;
		   int offset = 0;
		   
		   int hangul_byte_num = encoding.equals("UTF-8") ? 3 : 2;
		   
		   if(rawLength > len){
		    int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
		    ary = new String[aryLength];

		    for(int i=0; i<aryLength; i++){
		     minus_byte_num = 0;
		     offset = len;
		     if(index + offset > rawBytes.length){
		      offset = rawBytes.length - index;
		     }
		     for(int j=0; j<offset; j++){      
		      if(((int)rawBytes[index + j] & 0x80) != 0){
		       minus_byte_num ++;
		      }
		     }     
		     if(minus_byte_num % hangul_byte_num != 0){
		      offset -= minus_byte_num % hangul_byte_num;
		     }     
		     ary[i] = new String(rawBytes, index, offset, encoding);     
		     index += offset ;
		     
		    }    
		   } else {
		    ary = new String[]{raw};
		   }    
		  } catch(Exception e) {
		   
		  }     
		  return Arrays.toString(ary);
		 }

	  public static String cut(String s, int n) {
		  
		    byte[] utf8 = s.getBytes();
		    if (utf8.length < n) n = utf8.length;
		    int n16 = 0;
		    boolean extraLong = false;
		    int i = 0;
		    while (i < n) {
		      n16 += (extraLong) ? 2 : 1;
		      extraLong = false;
		      if ((utf8[i] & 0x80) == 0) i += 1;
		      else if ((utf8[i] & 0xC0) == 0x80) i += 2;
		      else if ((utf8[i] & 0xE0) == 0xC0) i += 3;
		      else { i += 4; extraLong = true; }
		    }
		    System.out.println( utf8.length );
		    System.out.println( n16 );
		    return s.substring(0,n16);
		    
		  }	
	  
		public static String stringCut(String sourceText, String startKeyword, int cutLength, int startKewordPreviousLength, boolean isTag, boolean isDot) {
				
			String targetText = sourceText;
			int oF = 0;
			int oL = 0;
			int rF = 0;
			int rL = 0;
			int nLengthPrev = 0;
			
			// 태그 제거 패턴
			Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);

			if (isTag) {
				targetText = p.matcher(targetText).replaceAll("");
			}
			
			// 태그 제거
			targetText = targetText.replaceAll("&amp;", "&");
			
			// 공백제거
			targetText = targetText.replaceAll("(!/|\r|\n|&nbsp;)", "");

			try {
				 // 바이트 보관
				byte[] bytes = targetText.getBytes("UTF-8");
				if (startKeyword != null && !startKeyword.equals("")) {
					nLengthPrev = (targetText.indexOf(startKeyword) == -1) ? 0 : targetText
							.indexOf(startKeyword); // 일단 위치찾고
					// 위치까지길이를  byte로 다시 구한다
					nLengthPrev = targetText.substring(0, nLengthPrev).getBytes("MS949").length;
					// 좀 앞부분부터 가져오도록한다.
					nLengthPrev = (nLengthPrev - startKewordPreviousLength >= 0) ? nLengthPrev - startKewordPreviousLength : 0; 
				}

				// x부터 y길이만큼 잘라낸다. 한글안깨지게.
				int j = 0;

				if (nLengthPrev > 0)
					while (j < bytes.length) {
						if ((bytes[j] & 0x80) != 0) {
							oF += 2;
							rF += 3;
							if (oF + 2 > nLengthPrev) {
								break;
							}
							j += 3;
						} else {
							if (oF + 1 > nLengthPrev) {
								break;
							}
							++oF;
							++rF;
							++j;
						}
					}

				j = rF;

				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						if (oL + 2 > cutLength) {
							break;
						}
						oL += 2;
						rL += 3;
						j += 3;
					} else {
						if (oL + 1 > cutLength) {
							break;
						}
						++oL;
						++rL;
						++j;
					}
				}

				// charset 옵션
				targetText = new String(bytes, rF, rL, "UTF-8");

				 // "..."를 붙일지 말지 옵션
				if (isDot && rF + rL + 3 <= bytes.length) {
					targetText += "...";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return targetText;
		}	
	
}
