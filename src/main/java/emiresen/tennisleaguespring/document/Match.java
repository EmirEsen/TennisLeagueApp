package emiresen.tennisleaguespring.document;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document("matches")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Match {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String court;
    private LocalDate date;
    private LocalTime time;
    @NotBlank
    @Indexed
    private String player1Id;

    @NotBlank
    @Indexed
    private String player2Id;

    private List<Score> score;
    private String winnerId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Score {
        private String player1Id;
        private int player1Score;
        private String player2Id;
        private int player2Score;
    }


}
