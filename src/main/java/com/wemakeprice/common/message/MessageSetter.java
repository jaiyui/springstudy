package com.wemakeprice.common.message;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

@Repository("MessageSetter") 
public class MessageSetter {

	private Map<?, ?> messages;

	/**
	 * 메세지 세팅
	 * @param messages
	 */
	public void setMessages(Map<?, ?> messages) {
		this.messages = messages;
	}

	/**
	 * 메세지 리턴
	 * @param code
	 * @return
	 */
	public String getMessages(String code) {
		return (String) code;
		//return (String) this.messages.get(code);
	}

	/**
	 * 0000 메세지 세팅 ( JSON )
	 * @param model
	 * @return
	 */
	public String set0000(Model model) {
		String code = "0000";
		model.addAttribute("result_no", code);
		model.addAttribute("error_desc", "");
		return "jsonViewer";		
	}


	/**
	 * 리턴 모델에 code와 message 세팅
	 * @param code
	 * @param msg
	 * @return
	 */
	public String setModelWithMessage(Model model, String code, String msg) {
		model.addAttribute("result_no", code);
		model.addAttribute("error_desc", msg);
		model.addAttribute("data", "null");
		return "jsonViewer";	
	}
	/**
	 * 1000 메세지 세팅
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> set1000() throws Exception {
		return (Map<String, ?>) setModel("1000"); 
	}


	/**
	 * 일반 Exception 메세지 세팅 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> setException(String code) throws Exception {
		String msg = this.getMessages(code);
		
		if(msg != null && !"".equals(msg)) {
			return (Map<String, ?>) setModelWithMessageEx(code, msg);
		} else {
			return null;
		}
	}
	
	/**
	 * 리턴 모델에 code 세팅
	 * @param code
	 * @return
	 */
	private ModelMap setModel(String code) {
		ModelMap model = new ModelMap();
		model.addAttribute("result_no", code);
		model.addAttribute("error_desc", this.getMessages(code));
		model.addAttribute("data", "null");
		return model;
	}


	/**
	 * 리턴 모델에 code와 message 세팅
	 * @param code
	 * @param msg
	 * @return
	 */
	public ModelMap setModelWithMessageEx(String code, String msg) {
		ModelMap model = new ModelMap();
		model.addAttribute("result_no", code);
		model.addAttribute("error_desc", msg);
		model.addAttribute("data", "null");
		return model;
	}
		
	
	
	
}


