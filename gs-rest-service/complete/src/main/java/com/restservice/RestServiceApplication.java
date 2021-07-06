package com.restservice;

import com.buslogic.PreaggUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {

        PreaggUtility preaggUtility = PreaggUtility.getInstance();
        preaggUtility.init();

        SpringApplication.run(RestServiceApplication.class, args);

    }

}
