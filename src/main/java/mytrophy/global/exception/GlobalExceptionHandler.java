//package mytrophy.global.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(CustomException.class)
//    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
//        log.error("CustomException", e);
//        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getHttpStatus(),
//                e.getErrorCode().getErrorCode(), e.getErrorCode().getMessage());
//
//        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
//    }
//
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
//        log.error("Exception", e);
//        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
//
//}
