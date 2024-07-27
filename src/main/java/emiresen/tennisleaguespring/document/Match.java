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

import java.time.LocalDateTime;
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

    @NotBlank
    @Indexed
    private String player1Id;

    @NotBlank
    @Indexed
    private String player2Id;

    private List<Score> setScores;
    private String winnerId;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
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

//    Match match = Match.builder()
//            .player1Id("player1IdHere")
//            .player2Id("player2IdHere")
//            .setScores(List.of(
//                    new Match.SetScore("player1IdHere", 6, "player2IdHere", 3),
//                    new Match.SetScore("player1IdHere", 4, "player2IdHere", 6),
//                    new Match.SetScore("player1IdHere", 7, "player2IdHere", 5)
//            ))
//            .winnerId("player1IdHere")
//            .build();

}
