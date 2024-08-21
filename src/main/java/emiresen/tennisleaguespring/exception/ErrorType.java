package emiresen.tennisleaguespring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType
{
    EMAIL_OR_PASSWORD_WRONG(1001, "Username or Password Wrong", HttpStatus.BAD_REQUEST),
    EMAIL_IS_USED(1002, "Email is already in use!", HttpStatus.BAD_REQUEST),
    PLAYER_NOT_FOUND(1004, "No such player with this email", HttpStatus.NOT_FOUND),
    NOT_AUTHORIZED(1019,"Not Authorized" ,HttpStatus.BAD_REQUEST),
    OFFER_NOT_FOUND(1020,"Offer Not Found" ,HttpStatus.BAD_REQUEST),
    MAIL_SEND_FAIL(3003,"Failed to send email." , HttpStatus.INTERNAL_SERVER_ERROR)
    ;
    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

}