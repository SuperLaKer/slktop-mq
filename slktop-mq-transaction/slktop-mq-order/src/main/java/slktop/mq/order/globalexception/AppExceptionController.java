package slktop.mq.order.globalexception;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@RestControllerAdvice
public class AppExceptionController {

    @ExceptionHandler(Exception.class)
    public String exceptionHandlerMethod(Exception e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }

}
