package emiresen.tennisleaguespring.exception;

import lombok.Getter;


@Getter
public class TennisLeagueAppException extends RuntimeException {

    private final ErrorType errorType;

    public TennisLeagueAppException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public TennisLeagueAppException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
