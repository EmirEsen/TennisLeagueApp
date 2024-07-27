package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.document.Match;
import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public void saveNewMatch(SaveNewMatchRequestDto dto) {
        List<Match.Score> setScores = dto.getSetScores().stream()
                .map(scoreDto -> new Match.Score(
                        dto.getPlayer1Id(),
                        scoreDto.getPlayer1Score(),
                        dto.getPlayer2Id(),
                        scoreDto.getPlayer2Score()
                ))
                .collect(Collectors.toList());

        Match match = Match.builder()
                .player1Id(dto.getPlayer1Id())
                .player2Id(dto.getPlayer2Id())
                .setScores(setScores)
                .winnerId(dto.getWinnerId())
                .build();

        matchRepository.save(match);

    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }
}
