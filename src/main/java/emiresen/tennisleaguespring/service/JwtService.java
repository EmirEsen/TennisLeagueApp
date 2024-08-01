package emiresen.tennisleaguespring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${Jwt.secret.key}")
    private String SECRET_KEY;

    private final Long expiration = 1000L*60*2;


    public String generateToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public String extractEmail(String token) {
        System.out.println("token sent: " + token);
        return JWT.decode(token).getSubject();
    }

}
