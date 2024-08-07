package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.dtos.request.PlayerProfileUpdateDto;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
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

    public PlayerProfileResponseDto update(PlayerProfileUpdateDto playerProfileUpdateDto) {

        Player playerToBeUpdated = playerRepository
                .findOptionalByEmail(getCurrentUserEmail())
                .orElseThrow();

        if (playerProfileUpdateDto.firstname() != null) {
            playerToBeUpdated.setFirstname(playerProfileUpdateDto.firstname());
        }
        if (playerProfileUpdateDto.lastname() != null) {
            playerToBeUpdated.setLastname(playerProfileUpdateDto.lastname());
        }
        if(playerProfileUpdateDto.heightInCm() != null){
            playerToBeUpdated.setHeightInCm(playerProfileUpdateDto.heightInCm());
        }
        if(playerProfileUpdateDto.weightInKg() != null){
            playerToBeUpdated.setWeightInKg(playerProfileUpdateDto.weightInKg());
        }
        if(playerProfileUpdateDto.dob() != null){
            playerToBeUpdated.setDob(playerProfileUpdateDto.dob());
        }

        playerRepository.save(playerToBeUpdated);

        // todo returning not updated profile just received parameters correct that.
        return PlayerProfileResponseDto.builder()
                .id(playerToBeUpdated.getId())
                .firstname(playerToBeUpdated.getFirstname())
                .lastname(playerToBeUpdated.getLastname())
                .email(playerToBeUpdated.getEmail())
                .heightInCm(playerToBeUpdated.getHeightInCm())
                .weightInKg(playerToBeUpdated.getWeightInKg())
                .rating(playerToBeUpdated.getRating())
                .dob(playerToBeUpdated.getDob())
                .avatarImage(playerToBeUpdated.getAvatarImage())
                .build();
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
                        .heightInCm(player.getHeightInCm())
                        .weightInKg(player.getWeightInKg())
                        .rating(player.getRating())
                        .win(player.getWin())
                        .lose(player.getLose())
                        .matchPlayed(player.getMatchPlayed())
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
                    .heightInCm(player.getHeightInCm())
                    .weightInKg(player.getWeightInKg())
                    .rating(player.getRating())
                    .matchPlayed(player.getMatchPlayed())
                    .win(player.getWin())
                    .lose(player.getLose())
                    .dob(player.getDob())
                    .avatarImage(player.getAvatarImage())
                    .build();


        }
        return null;
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
