package donggrami.earth1round.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<Object> handleBaseExceptions(BaseException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                .isSuccess(exception.getStatus().isSuccess())
                .code(exception.getStatus().getCode())
                .message(exception.getStatus().getMessage())
                .build();
        return new ResponseEntity(exceptionResponse, exception.getHttpStatus());
    }
}
