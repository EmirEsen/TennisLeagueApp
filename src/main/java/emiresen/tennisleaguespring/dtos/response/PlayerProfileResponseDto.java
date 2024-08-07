package emiresen.tennisleaguespring.dtos.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record PlayerProfileResponseDto(
        String id,
        String firstname,
        String lastname,
        String email,
        Date dob,
        String heightInCm,
        String weightInKg,
        Integer rating,
        Integer matchPlayed,
        Integer win,
        Integer lose,
        String avatarImage
) {}






