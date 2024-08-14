package emiresen.tennisleaguespring.repository;

import emiresen.tennisleaguespring.document.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Repository
public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByPlayer1IdOrPlayer2Id(String player1Id, String player2Id);

    @Query(value = "{}", sort = "{ 'time': -1 }")
    List<Match> findAll();
}
