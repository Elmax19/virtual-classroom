package com.softarex.test.classroom;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initialisation class
 *
 * @author Elmax19
 * @version 1.0
 * @see SpringBootServletInitializer
 */
public class ServletInitializer extends SpringBootServletInitializer {
    /**
     * method that add application into {@link SpringApplicationBuilder#sources}
     *
     * @param application current application
     * @return current application
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VirtualClassroomApplication.class);
    }
}
