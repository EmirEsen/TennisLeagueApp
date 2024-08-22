package emiresen.tennisleaguespring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body("Runtime Exception Occurred: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(TennisLeagueAppException.class)
    public ResponseEntity<ErrorObject> handleDemoException(TennisLeagueAppException ex) {
        ErrorType errorType = ex.getErrorType();
        ResponseEntity<ErrorObject> err = new ResponseEntity<>(createErrorMessage(ex,
                errorType),
                errorType.getHttpStatus());
        System.out.println(err);
        return err;
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorObject> handleAccess(TennisLeagueAppException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(createErrorMessage(ex,
                errorType),
                errorType.getHttpStatus());
    }

    private ErrorObject createErrorMessage(Exception ex, ErrorType errorType) {
        return ErrorObject.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

}