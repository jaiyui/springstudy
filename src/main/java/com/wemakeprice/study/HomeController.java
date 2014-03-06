package com.wemakeprice.study;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wemakeprice.common.message.MessageSetter;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Resource(name = "MessageSetter")
	private MessageSetter messageSetter;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public String json(Locale locale, Model model) throws Exception  {
		
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("A", "A");
		result.put("B", "B");
		model.addAttribute("data", result );
		
		return "jsonViewer";
	}

	@RequestMapping(value = "/json1", method = RequestMethod.GET)
	public String json1(Locale locale, Model model) throws Exception  {
		
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("A", "A");
		result.put("B", "B");
		model.addAttribute("data", result );
		
		return messageSetter.set0000(model);
	}
	
	@RequestMapping(value = "/json2", method = RequestMethod.GET)
	public String json2(Locale locale, Model model) throws Exception  {
		
		return messageSetter.setModelWithMessage(model, "0011", "AAAA");
	}
	
}
