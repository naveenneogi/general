package com.restservice;

import com.buslogic.PreaggUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        String filename = "/tmp/itemCountryPricing.csv";

        PreaggUtility preaggUtility = PreaggUtility.getInstance();
        preaggUtility.init(filename);

        SpringApplication.run(RestServiceApplication.class, args);
    }

}
