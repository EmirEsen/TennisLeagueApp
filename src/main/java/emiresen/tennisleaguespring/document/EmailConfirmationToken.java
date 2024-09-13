package emiresen.tennisleaguespring.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document
public class EmailConfirmationToken {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Indexed
    private String token;
    @CreatedDate
    @ReadOnlyProperty
    private LocalDateTime timeStamp;
    @DBRef
    private Player player;
}
