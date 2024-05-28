//package mytrophy.global.exception;
//
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.validation.FieldError;
//
//import java.util.List;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ErrorResponse {
//
//    private String errorCode;           //에러 구분 코드
//    private String errorMessage;        //에러 메세지
//
//    @Builder
//    protected ErrorResponse(final ErrorCodeEnum code) {
//        this.errorMessage = code.getMessage();
//        this.errorCode = code.getErrorCode();
//
//    }
//}
