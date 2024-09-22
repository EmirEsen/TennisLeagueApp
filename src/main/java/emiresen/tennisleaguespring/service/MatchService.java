package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.document.Match;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.dtos.response.MatchResponseDto;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerService playerService;
    private final EloRatingService eloRatingService;

    public MatchResponseDto saveNewMatch(SaveNewMatchRequestDto dto) {
        Optional<Player> playerAOpt = playerService.findById(dto.player1Id());
        Optional<Player> playerBOpt = playerService.findById(dto.player2Id());

        if (playerAOpt.isEmpty() || playerBOpt.isEmpty()) {
            throw new IllegalArgumentException("Both players must exist for the match to be recorded.");
        }

        Player playerA = playerAOpt.get();
        Player playerB = playerBOpt.get();

        // Set initial ratings if this is the first match for either player
        setInitialRatingIfFirstMatch(playerA);
        setInitialRatingIfFirstMatch(playerB);

        // Create and save match
        Match match = createMatchFromDto(dto);
        Match savedMatch = matchRepository.save(match);

        // Store current ratings before updating
        int playerARatingBefore = playerA.getRating();
        int playerBRatingBefore = playerB.getRating();

        // Update player stats and Elo ratings
        updatePlayerStats(playerA, playerB, savedMatch);

        // Calculate and store rating changes
        savedMatch.setPlayer1RatingChange(playerA.getRating() - playerARatingBefore);
        savedMatch.setPlayer2RatingChange(playerB.getRating() - playerBRatingBefore);
        matchRepository.save(savedMatch);

        return buildMatchResponseDto(savedMatch);
    }

    private void setInitialRatingIfFirstMatch(Player player) {
        if (isFirstMatch(player.getId())) {
            player.setRating(1200);
        }
    }

    private Match createMatchFromDto(SaveNewMatchRequestDto dto) {
        List<Match.Score> score = dto.score().stream()
                .map(scoreDto -> new Match.Score(
                        dto.player1Id(),
                        scoreDto.getPlayer1Score(),
                        dto.player2Id(),
                        scoreDto.getPlayer2Score()
                ))
                .toList();

        PlayerProfileResponseDto playerProfile = playerService
                .getPlayerProfileByEmail(playerService.getCurrentUserEmail());

        return Match.builder()
                .court(dto.court())
                .date(dto.date())
                .time(dto.time())
                .player1Id(dto.player1Id())
                .player2Id(dto.player2Id())
                .score(score)
                .winnerId(determineWinner(dto))
                .createdById(playerProfile.id())
                .build();
    }

    private void updatePlayerStats(Player player1, Player player2, Match saved) {
        setInitialRatingIfFirstMatch(player1);
        setInitialRatingIfFirstMatch(player2);

        int score1 = (saved.getWinnerId().equals(player1.getId())) ? 1 : 0;
        int score2 = (saved.getWinnerId().equals(player2.getId())) ? 1 : 0;

        player1.setMatchPlayed(player1.getMatchPlayed() == null ? 1 : player1.getMatchPlayed() + 1);
        player2.setMatchPlayed(player2.getMatchPlayed() == null ? 1 : player2.getMatchPlayed() + 1);

        if (score1 > score2) {
            updateWinLose(player2, player1);
        } else {
            updateWinLose(player1, player2);
        }


        eloRatingService.updatePlayerRatings(player1, player2, score1, score2);

        playerService.save(player1);
        playerService.save(player2);
    }

    private void updateWinLose(Player player1, Player player2) {
        player2.setWin(player2.getWin() == null ? 1 : player2.getWin() + 1);
        player2.setLose(player2.getLose() == null ? 0 : player2.getLose());
        player1.setLose(player1.getLose() == null ? 1 : player1.getLose() + 1);
        player1.setWin(player1.getWin() == null ? 0 : player1.getWin());
    }


    private MatchResponseDto buildMatchResponseDto(Match saved) {
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

        for (Match.Score score : dto.score()) {
            if (score.getPlayer1Score() > score.getPlayer2Score()) {
                player1Wins++;
            } else if (score.getPlayer2Score() > score.getPlayer1Score()) {
                player2Wins++;
            }
        }

        if (player1Wins > player2Wins) {
            return dto.player1Id();
        } else if (player2Wins > player1Wins) {
            return dto.player2Id();
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
                        .createdAt(match.getCreatedAt())
                        .build()
        ).toList();
        return matchResponseDtos;
    }

    public List<MatchResponseDto> findLatestSix() {
        return matchRepository.findAll().stream()
                .sorted(Comparator.comparing(Match::getCreatedAt).reversed())
                .limit(6)
                .map(match ->
                        MatchResponseDto.builder()
                                .id(match.getId())
                                .player1Id(match.getPlayer1Id())
                                .player2Id(match.getPlayer2Id())
                                .score(match.getScore())
                                .winnerId(match.getWinnerId())
                                .court(match.getCourt())
                                .date(match.getDate())
                                .time(match.getTime())
                                .createdAt(match.getCreatedAt())
                                .player1RatingChange(match.getPlayer1RatingChange())
                                .player2RatingChange(match.getPlayer2RatingChange())
                                .build()
                ).toList();
    }

    public boolean isFirstMatch(String playerId) {
        Player player = playerService.findById(playerId).orElseThrow();
        return player.getMatchPlayed() == null;
    }
}
