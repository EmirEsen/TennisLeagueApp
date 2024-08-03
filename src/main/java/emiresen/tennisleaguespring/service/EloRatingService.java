package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.document.Player;
import org.springframework.stereotype.Service;

@Service
public class EloRatingService {

    private static final int K = 32;

    public double calculateExpectedScore(int ratingA, int ratingB) {
        return 1.0 / (1.0 + Math.pow(10, (ratingB - ratingA) / 400.0));
    }

    public int updateRating(int currentRating, double actualScore, double expectedScore) {
        return (int) (currentRating + K * (actualScore - expectedScore));
    }

    public void updatePlayerRatings(Player playerA, Player playerB, int scoreA, int scoreB) {
        double expectedScoreA = calculateExpectedScore(playerA.getRating(), playerB.getRating());
        double expectedScoreB = calculateExpectedScore(playerB.getRating(), playerA.getRating());

        int newRatingA = updateRating(playerA.getRating(), scoreA, expectedScoreA);
        int newRatingB = updateRating(playerB.getRating(), scoreB, expectedScoreB);

        playerA.setRating(newRatingA);
        playerB.setRating(newRatingB);
    }
}
