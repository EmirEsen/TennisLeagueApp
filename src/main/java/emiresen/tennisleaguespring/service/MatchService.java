package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.document.Match;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.dtos.response.MatchResponseDto;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerService playerService;
    private final EloRatingService eloRatingService;

    public MatchResponseDto saveNewMatch(SaveNewMatchRequestDto dto) {
        Optional<Player> playerA = playerService.findById(dto.getPlayer1Id());
        Optional<Player> playerB = playerService.findById(dto.getPlayer2Id());

        List<Match.Score> score = dto.getScore().stream()
                .map(scoreDto -> new Match.Score(
                        dto.getPlayer1Id(),
                        scoreDto.getPlayer1Score(),
                        dto.getPlayer2Id(),
                        scoreDto.getPlayer2Score()
                ))
                .toList();

        PlayerProfileResponseDto playerProfile = playerService
                .getPlayerProfileByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        Match match = Match.builder()
                .court(dto.getCourt())
                .date(dto.getDate())
                .time(dto.getTime())
                .player1Id(dto.getPlayer1Id())
                .player2Id(dto.getPlayer2Id())
                .score(score)
                .winnerId(determineWinner(dto))
                .createdById(playerProfile.id())
                .build();

            Match saved = matchRepository.save(match);

        if (playerA.isPresent() && playerB.isPresent()) {
            Player player1 = playerA.get();
            Player player2 = playerB.get();

            int scoreA = (saved.getWinnerId().equals(player1.getId())) ? 1 : 0;
            int scoreB = (saved.getWinnerId().equals(player2.getId())) ? 1 : 0;

            eloRatingService.updatePlayerRatings(player1, player2, scoreA, scoreB);

            playerService.save(player1);
            playerService.save(player2);
        }

        return MatchResponseDto.builder()
                        .id(saved.getId())
                        .court(saved.getCourt())
                        .date(saved.getDate())
                        .time(saved.getTime())
                        .player1Id(saved.getPlayer1Id())
                        .player2Id(saved.getPlayer2Id())
                        .score(saved.getScore())
                        .winnerId(saved.getWinnerId())
                        .build();
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
