package emiresen.tennisleaguespring.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlayerRegisterRequestDto(String firstname,
                                       String lastname,
                                       @Email(message = "Enter valid email.") String email,
                                       @NotBlank String password) {
}
