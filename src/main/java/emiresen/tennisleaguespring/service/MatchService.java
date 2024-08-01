package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.document.Match;
import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.dtos.response.MatchResponseDto;
import emiresen.tennisleaguespring.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public void saveNewMatch(SaveNewMatchRequestDto dto) {
        List<Match.Score> score = dto.getScore().stream()
                .map(scoreDto -> new Match.Score(
                        dto.getPlayer1Id(),
                        scoreDto.getPlayer1Score(),
                        dto.getPlayer2Id(),
                        scoreDto.getPlayer2Score()
                ))
                .toList();

        Match match = Match.builder()
                .court(dto.getCourt())
                .date(dto.getDate())
                .time(dto.getTime())
                .player1Id(dto.getPlayer1Id())
                .player2Id(dto.getPlayer2Id())
                .score(score)
                .winnerId(determineWinner(dto))
                .createdAt(LocalDateTime.now())
                .build();

        matchRepository.save(match);

    }

    public String determineWinner(SaveNewMatchRequestDto dto) {
        int player1Wins = 0;
        int player2Wins = 0;

        for (Match.Score score : dto.getScore()) {
            if (score.getPlayer1Score() > score.getPlayer2Score()) {
                player1Wins++;
            } else if (score.getPlayer2Score() > score.getPlayer1Score()) {
                player2Wins++;
            }
        }

        if (player1Wins > player2Wins) {
            return dto.getPlayer1Id();
        } else if (player2Wins > player1Wins) {
            return dto.getPlayer2Id();
        } else {
            return "draw";  //todo think about this.
        }
    }

    public List<MatchResponseDto> findAll() {
        List<MatchResponseDto> matchResponseDtos;
        matchResponseDtos = matchRepository.findAll().stream().map(match ->
                MatchResponseDto.builder()
                        .id(match.getId())
                        .player1Id(match.getPlayer1Id())
                        .player2Id(match.getPlayer2Id())
                        .score(match.getScore())
                        .winnerId(match.getWinnerId())
                        .court(match.getCourt())
                        .date(match.getDate())
                        .time(match.getTime())
                        .build()
        ).toList();
        return matchResponseDtos;
    }
}
