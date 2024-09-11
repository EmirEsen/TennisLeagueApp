package emiresen.tennisleaguespring.repository;

import emiresen.tennisleaguespring.document.EmailConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmailConfirmationTokenRepository extends MongoRepository<EmailConfirmationToken, String> {

    Optional<EmailConfirmationToken> findByToken(String token);

}
