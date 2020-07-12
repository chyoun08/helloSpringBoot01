package com.kissco.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public String home(Model model) {
		
		logger.info("Info: Calling home( )" );
		logger.debug("Debug: Calling home( )" );
		logger.trace("trace: Calling home( )" );
		
		model.addAttribute("message","hello Word");
		
		return "index";
	}
}
