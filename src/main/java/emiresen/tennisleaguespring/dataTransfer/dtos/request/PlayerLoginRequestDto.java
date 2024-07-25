package emiresen.tennisleaguespring.dataTransfer.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerLoginRequestDto {
    private String email;
    private String password;
}
