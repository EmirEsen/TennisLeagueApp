package emiresen.tennisleaguespring.repository;

import emiresen.tennisleaguespring.document.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;


@Repository
public interface MatchRepository extends MongoRepository<Match, String> {


}
