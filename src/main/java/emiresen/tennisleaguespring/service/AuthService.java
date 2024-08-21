package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.document.Role;
import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.AuthenticationResponse;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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


    public AuthenticationResponse register(PlayerRegisterRequestDto dto) {
        Player newPlayer = Player.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();
        playerRepository.save(newPlayer);
        String token = jwtService.generateToken(newPlayer);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }


    public String login(PlayerLoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Player authenticatedPlayer = playerRepository.findOptionalByEmail(dto.getEmail()).get();
        String token = jwtService.generateToken(authenticatedPlayer);
        System.out.println("Generated token at login: " + token);
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getPrincipal());
        return token;
    }
}
