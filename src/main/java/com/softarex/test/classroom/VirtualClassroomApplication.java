package com.softarex.test.classroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring initialisation class
 * @author Elmax19
 * @version 1.0
 */
@SpringBootApplication
public class VirtualClassroomApplication {
    /**
     * main class which run {@link SpringApplication}
     * @param args input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VirtualClassroomApplication.class, args);
    }
}
