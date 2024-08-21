package emiresen.tennisleaguespring.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlayerLoginRequestDto(@NotBlank String email, @NotBlank String password) {

}
