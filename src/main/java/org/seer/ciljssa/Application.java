package org.seer.ciljssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * TODO: Need to get what methods are called inside each method and have a wrapper for those
 *       Annotation wrapper needs to be very fleshed out
 *       Analysis context should contain information about role hierarchy
 *       Method wrapper should show allow roles and similar information if it is available from the annotation wrapper
 *       Annotation wrapper should contain info about what security roles are allowed:
 *          - If nothing is specified, all roles can access or the lowest of whatever role has accessed it?
 *       Fix some of the message handling
 */

@SpringBootApplication
public class Application {

    static final boolean DEBUG = false;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        if (DEBUG) {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        }
    }
}
