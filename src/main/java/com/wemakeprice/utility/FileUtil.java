package com.wemakeprice.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.util.FileCopyUtils;

public class FileUtil {

	/**
	 * 이미지 파일을 임시로 저장한다: 메일 본문에 포함된 이미지
	 * @param path
	 * @param fileName
	 * @param bo
	 * @throws Exception
	 */
	public static void createdFile(String path, String fileName, byte[] bo) throws Exception {
		File dir = new File(path);
		if(dir == null || !dir.exists() || !dir.isDirectory()) {
			if(!dir.mkdir()) {
				throw new Exception();
			}
		}
		
		File file = new File(path + fileName);
		
		if(file.exists() && file.length() > 0) {
			//이미 있으면 있는 파일을 그대로 사용
		} else {
			FileOutputStream fOut = new FileOutputStream(file);
			
			try {
				FileCopyUtils.copy(bo, fOut);
				fOut.flush();
			} catch(Exception e) {
				throw e;
			} finally {
				if(fOut != null) {
					fOut.close();
					fOut = null;
				}
			}
		}
	}

	/**
	 * 파일을 string으로 반환
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String getFileString(InputStream in) throws Exception {
		ByteArrayOutputStream bo = null;
		
		try {
			bo = getFileStream(in);
			return bo.toString();
		} catch(Exception e) {
			throw e;
		} finally {
			if(bo != null) {
				bo.close();
				bo = null;
			}
			if(in != null) {
				in.close();
				in = null;
			}
		}
	}

	/**
	 * 파일을 byte[]로 반환
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileBytes(InputStream in) throws Exception {
		ByteArrayOutputStream bo = null;
		
		try {
			bo = getFileStream(in);
			return bo.toByteArray();
		} catch(Exception e) {
			throw e;
		} finally {
			if(bo != null) {
				bo.close();
				bo = null;
			}
			if(in != null) {
				in.close();
				in = null;
			}
		}
	}

	/**
	 * inputStream 을 outputStream으로 반환
	 * @param in
	 * @return
	 * @throws Exception
	 */
	private static ByteArrayOutputStream getFileStream(InputStream in) throws Exception {
		ByteArrayOutputStream bo = null;
		
		if(in != null) {
			bo = new ByteArrayOutputStream();

			FileCopyUtils.copy(in, bo);
			bo.flush();
				
			return bo;
		}
		return null;
	}
}
