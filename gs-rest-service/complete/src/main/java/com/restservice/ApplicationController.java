package com.restservice;

import java.util.concurrent.atomic.AtomicLong;

import com.buslogic.PreaggUtility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}


    @GetMapping("/getItemCountryPricing")
    public String getItemCountryPricing(
            @RequestParam(value = "item", defaultValue = "mango") String item,
            @RequestParam(value = "quantity", defaultValue = "1") Float multiplier
    ) {

	    return PreaggUtility.getInstance().getItemCountryPricing(item);
    }


}
