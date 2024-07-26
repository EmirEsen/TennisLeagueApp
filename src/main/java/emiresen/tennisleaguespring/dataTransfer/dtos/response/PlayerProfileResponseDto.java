package emiresen.tennisleaguespring.dataTransfer.dtos.response;


import emiresen.tennisleaguespring.document.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
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
