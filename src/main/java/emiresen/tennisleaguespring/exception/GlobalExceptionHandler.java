package emiresen.tennisleaguespring.exception;

// Bu sınıf tüm controller sınıfları için merkezi bir şekilde hata yönetimi sağlayacaktır.

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body("Runtime Exception Occurred: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


//    @ExceptionHandler(HumanResourcesAppException.class)
//    public ResponseEntity<ErrorMessage> handleDemoException(HumanResourcesAppException ex) {
//        ErrorType errorType = ex.getErrorType();
//        return new ResponseEntity(createErrorMessage(ex,
//                errorType),
//                errorType.getHttpStatus());
//    }
//
//   /* @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorMessage> handleAccess(HumanResourcesAppException ex)
//    {
//        ErrorType errorType = ex.getErrorType();
//        return new ResponseEntity(createErrorMessage(ex,
//                errorType),
//                errorType.getHttpStatus());
//    }*/
//
//
//    private ErrorMessage createErrorMessage(Exception ex, ErrorType errorType) {
//        return ErrorMessage.builder()
//                .code(errorType.getCode())
//                .message(errorType.getMessage())
//                .build();
//    }
//
//    private ErrorMessage createErrorMessage(MethodArgumentNotValidException ex) {
//
//
//        List<String> fieldErrors = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ErrorMessage errorMessage = ErrorMessage.builder()
//                .code(ex.hashCode())
//                .message(fieldErrors.toString()) // Or any general message
//                .build();
//
//        errorMessage.setFields(fieldErrors);
//        return errorMessage;
//    }
//
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException exception) {
//        System.out.println("Validation error occurred!");
//        ErrorType errorType = ErrorType.BAD_REQUEST_ERROR;
//        List<String> fields = new ArrayList<>();
//        exception
//                .getBindingResult()
//                .getFieldErrors()
//                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
//        ErrorMessage errorMessage = createErrorMessage(exception);
//        errorMessage.setFields(fields);
//        return new ResponseEntity<>(errorMessage,
//                errorType.getHttpStatus());
//    }
}