package emiresen.tennisleaguespring.dtos.request;


import emiresen.tennisleaguespring.document.Match;
import lombok.Builder;
import java.time.LocalDate;
import java.util.List;

@Builder
public record SaveNewMatchRequestDto(String court,
                                     LocalDate date,
                                     String time,
                                     String player1Id,
                                     String player2Id,
                                     List<Match.Score> score) {
}
