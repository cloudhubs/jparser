package edu.baylor.ecs.cloudhubs.jparser.app;

import edu.baylor.ecs.cloudhubs.jparser.app.response.BadResponse;
import edu.baylor.ecs.cloudhubs.jparser.app.response.BaseResponse;
import edu.baylor.ecs.cloudhubs.jparser.app.response.ResponseCode;
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
        public BaseResponse requestHandlingNoHandlerFound(final NoHandlerFoundException ex) {
            String error = "The request method was not found.\n" +  ex.getHttpMethod() + ": " + ex.getRequestURL() + ex.toString();
            BaseResponse response = new BadResponse();
            response.setResponseCode(ResponseCode.NOT_FOUND);
            response.setResponseMessage(error);
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
