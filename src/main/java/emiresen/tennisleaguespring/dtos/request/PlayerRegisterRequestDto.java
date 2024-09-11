package emiresen.tennisleaguespring.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlayerRegisterRequestDto(String firstname,
                                       String lastname,
                                       String email,
                                       @NotBlank String password) {
}
