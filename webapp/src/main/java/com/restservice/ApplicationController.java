package com.restservice;

import com.buslogic.PreaggUtility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApplicationController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();




    @GetMapping("/getItemCountryPricing")
    public String getItemCountryPricing(
            @RequestParam(value = "item", defaultValue = "mango") String item,
            @RequestParam(value = "quantity", defaultValue = "1") Float multiplier
    ) {

	    return PreaggUtility.getInstance().getItemCountryPricing(item);
    }


}
