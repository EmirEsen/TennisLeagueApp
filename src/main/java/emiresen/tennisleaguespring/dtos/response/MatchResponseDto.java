package emiresen.tennisleaguespring.dtos.response;

import emiresen.tennisleaguespring.document.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponseDto {

    private String id;
    private String court;
    private LocalDate date;
    private String time;
    private String player1Id;
    private String player2Id;
    private LocalDateTime createdAt;

    private List<Match.Score> score;
    private String winnerId;

}
