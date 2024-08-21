package emiresen.tennisleaguespring.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorObject {

    private Integer code;
    private String message;
    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

}
