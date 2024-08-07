package emiresen.tennisleaguespring.dtos.request;

import lombok.Builder;

import java.util.Date;

@Builder
public record PlayerProfileUpdateDto(
        String firstname,
        String lastname,
        String heightInCm,
        String weightInKg,
        Date dob
        ) {
}
