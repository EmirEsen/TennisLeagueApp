package emiresen.tennisleaguespring.dtos.request;

import lombok.Builder;

@Builder
public record PlayerProfileUpdateDto(
        String firstname,
        String lastname,
        String email,
        String password,
        Double heightInCm,
        Double weightInKg,
        Integer rating
        ) {
}
