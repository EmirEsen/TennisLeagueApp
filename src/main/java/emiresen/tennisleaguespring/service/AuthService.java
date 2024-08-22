package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.document.Role;
import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.exception.ErrorType;
import emiresen.tennisleaguespring.exception.TennisLeagueAppException;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseDto<String> register(PlayerRegisterRequestDto dto) {
        Player newPlayer = Player.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();
        try{
            playerRepository.save(newPlayer);
        }catch (RuntimeException e){
            throw new TennisLeagueAppException(ErrorType.EMAIL_IN_USE);
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
}
