package edu.baylor.ecs.ciljssa.app;

import edu.baylor.ecs.ciljssa.app.response.IHandledResponse;
import edu.baylor.ecs.ciljssa.app.response.ResponseBad;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * TODO: Need to get what methods are called inside each method and have a wrapper for those
 *       Annotation wrapper needs to be very fleshed out
 *       Analysis context should contain information about role hierarchy
 *       Method wrapper should show allow roles and similar information if it is available from the annotation wrapper
 *       Annotation wrapper should contain info about what security roles are allowed:
 *          - If nothing is specified, all roles can access or the lowest of whatever role has accessed it?
 *       Fix some of the message handling
 *       Make sure if no context is provided that the result is not succeeded
 */

@SpringBootApplication
public class Application {

    static final boolean DEBUG = false;

    @Bean
    DispatcherServlet dispatcherServlet () {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @EnableWebMvc
    @ControllerAdvice
    public class GlobalControllerExceptionHandler {
        @ExceptionHandler
        @ResponseStatus(value= HttpStatus.NOT_FOUND)
        @ResponseBody
        public IHandledResponse requestHandlingNoHandlerFound(final NoHandlerFoundException ex) {
            IHandledResponse response = new ResponseBad();
            response.setHttpStatus(404);
            String[] error = {"The request method was not found.", ex.getHttpMethod() + ": " + ex.getRequestURL() + ex.toString()};
            return response;
        }
    }

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
