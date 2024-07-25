package emiresen.tennisleaguespring.document;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("players")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Player {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Date dob;
    private Double height;
    private Double weight;
    private Integer rating;
    private String avatarImage;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}



