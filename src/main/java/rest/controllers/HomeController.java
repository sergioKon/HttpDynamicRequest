package rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/home")
	public String home() {
		LOGGER.info(" controller start ");
		return "Welcome to home!";
    }
}
