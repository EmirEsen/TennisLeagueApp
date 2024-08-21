package emiresen.tennisleaguespring.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorObject
{
    private Integer code;
    private String message;
    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
}
