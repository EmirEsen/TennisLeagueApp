package emiresen.tennisleaguespring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    EMAIL_OR_PASSWORD_WRONG(1001, "Email or Password Wrong", HttpStatus.BAD_REQUEST),
    EMAIL_IN_USE(1002, "Someone’s already using that email", HttpStatus.BAD_REQUEST),
    PLAYER_NOT_FOUND(1004, "No such player with this email", HttpStatus.NOT_FOUND),
    NOT_AUTHORIZED(1005, "Not Authorized", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1006, "Invalid Email", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1007, "Invalid Token", HttpStatus.BAD_REQUEST);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}