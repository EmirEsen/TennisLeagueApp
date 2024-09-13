package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.document.EmailConfirmationToken;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.document.Role;
import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.exception.ErrorType;
import emiresen.tennisleaguespring.exception.TennisLeagueAppException;
import emiresen.tennisleaguespring.repository.EmailConfirmationTokenRepository;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailValidator emailValidator;
    private final EmailService emailService;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public ResponseDto<String> register(PlayerRegisterRequestDto dto) {
        boolean isValidEmail = emailValidator.test(dto.email());
        if (!isValidEmail) {
            System.out.println("Invalid Email");
            throw new TennisLeagueAppException(ErrorType.INVALID_EMAIL);
        }

        Player newPlayer = Player.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .isEmailVerified(false)
                .build();
        try {
            playerRepository.save(newPlayer);
        } catch (RuntimeException e) {
            throw new TennisLeagueAppException(ErrorType.EMAIL_IN_USE);
        }

        try {
            sendRegistrationConfirmationEmail(newPlayer);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return ResponseDto.<String>builder()
                .code(200)
                .message("Successfully registered")
                .build();
    }

    public String login(PlayerLoginRequestDto dto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        } catch (AuthenticationException e) {
            throw new TennisLeagueAppException(ErrorType.EMAIL_OR_PASSWORD_WRONG);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Player authenticatedPlayer = playerRepository.findOptionalByEmail(dto.email())
                .orElseThrow(() -> new TennisLeagueAppException(ErrorType.PLAYER_NOT_FOUND));
        return jwtService.generateToken(authenticatedPlayer);
    }

    public void sendRegistrationConfirmationEmail(Player player) throws MessagingException {
        // Check for existing tokens related to the player
        List<EmailConfirmationToken> existingTokens = emailConfirmationTokenRepository.findByPlayer(player);

        // Delete existing tokens
        if (existingTokens != null && !existingTokens.isEmpty()) {
            emailConfirmationTokenRepository.deleteAll(existingTokens);
        }

        // Generate the token
        String token = jwtService.generateEmailVerificationToken(player);
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();
        emailConfirmationToken.setToken(token);
        emailConfirmationToken.setTimeStamp(LocalDateTime.now());
        emailConfirmationToken.setPlayer(player);
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        // Send email
        emailService.sendConfirmationEmail(emailConfirmationToken);
    }

    public boolean verifyPlayerEmail(String token) {
        Optional<EmailConfirmationToken> emailConfirmationToken = emailConfirmationTokenRepository.findByToken(token);

        if (emailConfirmationToken.isEmpty() || !token.equals(emailConfirmationToken.get().getToken())) {
            throw new TennisLeagueAppException(ErrorType.INVALID_TOKEN);
        }
        Player player = emailConfirmationToken.get().getPlayer();
        player.setIsEmailVerified(true);
        playerRepository.save(player);
        emailConfirmationTokenRepository.delete(emailConfirmationToken.get());
        return true;
    }

    public void resendConfirmationEmail(String email) {
        testFindOptionalByEmail(email);
        System.out.println("Requested email: " + email);
        Optional<Player> optionalPlayer = playerRepository.findOptionalByEmail(email);

        if(optionalPlayer.isPresent()) {
            System.out.println("Player found: " + optionalPlayer.get());
            try {
                sendRegistrationConfirmationEmail(optionalPlayer.get());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No player found with email: " + email);
        }
    }

    public void testFindOptionalByEmail(String email) {
        Optional<Player> player = playerRepository.findOptionalByEmail(email);
        System.out.println(player.map(value -> "Player found: " + value).orElse("No player found."));
    }
}
