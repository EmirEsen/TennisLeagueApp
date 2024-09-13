package emiresen.tennisleaguespring.repository;

import emiresen.tennisleaguespring.document.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findAllByIsEmailVerifiedTrue();

    Optional<Player> findOptionalByEmail(String email);
}
