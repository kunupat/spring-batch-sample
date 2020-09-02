package com.example.batchprocessing;

import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcessingApplication {

    public static void main(String[] args) throws Exception {
    	System.out.println("program args:");
    	for (String arg : args) {
			System.out.println(arg);
		}
        SpringApplication.run(BatchProcessingApplication.class, args);
    }
}
