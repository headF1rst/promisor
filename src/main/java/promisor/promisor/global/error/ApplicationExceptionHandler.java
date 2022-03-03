package promisor.promisor.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import promisor.promisor.global.error.exception.ApplicationException;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ErrorResponse> handleApplicationException(final ApplicationException err) {
        log.error("handleEntityNotFoundException", err);
        final ErrorCode errorCode = err.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getHttpStatus()));
    }

}
