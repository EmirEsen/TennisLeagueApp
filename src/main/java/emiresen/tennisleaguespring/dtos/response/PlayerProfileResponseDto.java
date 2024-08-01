package emiresen.tennisleaguespring.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
public record PlayerProfileResponseDto(
        String id,
        String firstname,
        String lastname,
        String email,
        Date dob,
        Double height,
        Double weight,
        Integer rating,
        String avatarImage) {}



