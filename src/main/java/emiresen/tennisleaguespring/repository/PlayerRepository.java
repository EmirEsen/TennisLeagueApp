package emiresen.tennisleaguespring.repository;

import emiresen.tennisleaguespring.document.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {

    boolean existsByEmail(String email);

    Optional<Player> findOptionalByEmail(String email);
}
