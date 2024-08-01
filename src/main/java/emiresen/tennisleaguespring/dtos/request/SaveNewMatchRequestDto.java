package emiresen.tennisleaguespring.dtos.request;


import emiresen.tennisleaguespring.document.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveNewMatchRequestDto {

    private String court;
    private LocalDate date;
    private LocalTime time;
    private String player1Id;
    private String player2Id;

    private List<Match.Score> score;

}
