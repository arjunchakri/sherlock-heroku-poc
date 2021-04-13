package com.sherlock.herokupoc.spring.core;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

	@RequestMapping("/")
	public ModelAndView showMainPage() {
		ModelAndView modelAndView = new ModelAndView("index");
		String newReaderKey = generateReaderKey();
		System.out.println("newReaderKey=" + newReaderKey);
		modelAndView.addObject("readerKey", newReaderKey);
		return modelAndView;
	}

	public static String generateReaderKey() {
		return UUID.randomUUID()
				.toString();
	}

}
