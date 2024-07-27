package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.AuthenticationResponse;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.document.Role;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(PlayerRegisterRequestDto dto) {
        Player newPlayer = Player.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        playerRepository.save(newPlayer);
        String token = jwtService.generateToken(newPlayer);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }


    public AuthenticationResponse login(PlayerLoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        Player authenticatedPlayer = playerRepository.findOptionalByEmail(dto.getEmail()).get();
        String token = jwtService.generateToken(authenticatedPlayer);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }


    public PlayerProfileResponseDto getPlayerProfile(String email) {
        Player player = playerRepository.findOptionalByEmail(email)
                .orElse(null);
        if (player != null) {
            return PlayerProfileResponseDto.builder()
                    .firstname(player.getFirstname())
                    .lastname(player.getLastname())
                    .email(player.getEmail())
                    .height(player.getHeight())
                    .weight(player.getWeight())
                    .rating(player.getRating())
                    .dob(player.getDob())
                    .avatarImage(player.getAvatarImage())
                    .build();
        }
        return null;
    }
}
