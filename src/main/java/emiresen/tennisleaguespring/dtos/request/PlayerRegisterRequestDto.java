package emiresen.tennisleaguespring.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerRegisterRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
