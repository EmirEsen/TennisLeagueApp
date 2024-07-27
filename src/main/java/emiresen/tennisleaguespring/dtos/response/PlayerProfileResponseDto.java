package emiresen.tennisleaguespring.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfileResponseDto {

    private String firstname;
    private String lastname;
    private String email;
    private Date dob;
    private Double height;
    private Double weight;
    private Integer rating;
    private String avatarImage;


}
