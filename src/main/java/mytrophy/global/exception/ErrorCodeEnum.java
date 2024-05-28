//package mytrophy.global.exception;
//
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//
//@Getter
//public enum ErrorCodeEnum {
//
//    //댓글
//    NOT_EXISTS_MEMBER_ID(HttpStatus.NOT_FOUND, "C-001", "존재하지 않는 회원입니다."),
//    NOT_EXISTS_COMMENT_ID(HttpStatus.NOT_FOUND, "C-002", "존재하지 않는 댓글입니다."),
//    ALREADY_LIKED_COMMENT_ID(HttpStatus.BAD_REQUEST, "C-003", "이미 추천한 댓글입니다."),
//    NOT_LIKED_COMMENT_ID(HttpStatus.BAD_REQUEST, "C-004", "추천하지 않은 댓글입니다.");
//
//    private final HttpStatus httpStatus;
//    private final String errorCode;
//    private final String message;
//
//    ErrorCodeEnum(HttpStatus httpStatus, String errorCode, String message) {
//        this.httpStatus = httpStatus;
//        this.errorCode = errorCode;
//        this.message = message;
//    }
//}
