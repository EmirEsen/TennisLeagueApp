package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.AuthenticationResponse;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Optional<Player> save(Player player) {
        Player playerSaved = playerRepository.save(player);
        return Optional.of(playerSaved);
    }

    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    public List<PlayerProfileResponseDto> findAll() {
        List<PlayerProfileResponseDto> playerProfileResponseDtos;
        playerProfileResponseDtos = playerRepository.findAll().stream().map(player ->
                PlayerProfileResponseDto.builder()
                        .id(player.getId())
                        .firstname(player.getFirstname())
                        .lastname(player.getLastname())
                        .email(player.getEmail())
                        .height(player.getHeight())
                        .weight(player.getWeight())
                        .rating(player.getRating())
                        .dob(player.getDob())
                        .avatarImage(player.getAvatarImage())
                        .build()
        ).toList();
        return playerProfileResponseDtos;
    }


    public PlayerProfileResponseDto getPlayerProfileByEmail(String email) {
        Player player = playerRepository.findOptionalByEmail(email)
                .orElse(null);
        if (player != null) {
            return PlayerProfileResponseDto.builder()
                    .id(player.getId())
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
