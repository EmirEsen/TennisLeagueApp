package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.config.s3.S3Buckets;
import emiresen.tennisleaguespring.dtos.request.PlayerProfileUpdateDto;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

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
        if (playerProfileUpdateDto.heightInCm() != null) {
            playerToBeUpdated.setHeightInCm(playerProfileUpdateDto.heightInCm());
        }
        if (playerProfileUpdateDto.weightInKg() != null) {
            playerToBeUpdated.setWeightInKg(playerProfileUpdateDto.weightInKg());
        }
        if (playerProfileUpdateDto.dob() != null) {
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
                .profileImageUrl(playerToBeUpdated.getProfileImageId())
                .build();
    }

    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    public List<PlayerProfileResponseDto> findAll() {
        return playerRepository.findAllByIsEmailVerifiedTrue().stream()
                .map(player -> {
                    String profileImageUrl = null;
                    try {
                        profileImageUrl = s3Service.createPresignedGetUrl(
                                s3Buckets.getCustomer(), "profile-images/%s/%s".formatted(player.getId(), player.getProfileImageId()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.err.println("Failed to generate S3 URL for player: " + player.getId());
                    }

                    Integer rating = (player.getRating() != null && player.getMatchPlayed() > 2) ? player.getRating() : null;

                    return PlayerProfileResponseDto.builder()
                            .id(player.getId())
                            .firstname(player.getFirstname())
                            .lastname(player.getLastname())
                            .email(player.getEmail())
                            .heightInCm(player.getHeightInCm())
                            .weightInKg(player.getWeightInKg())
                            .rating(rating)
                            .win(player.getWin())
                            .lose(player.getLose())
                            .matchPlayed(player.getMatchPlayed())
                            .dob(player.getDob())
                            .profileImageUrl(profileImageUrl)
                            .build();
                })
                .sorted(Comparator.comparing(PlayerProfileResponseDto::rating,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }


    public PlayerProfileResponseDto getPlayerProfileByEmail(String email) {
        Player player = playerRepository.findOptionalByEmail(email)
                .orElse(null);

        if (player != null) {
            String profileImageUrl = null;
            if (player.getProfileImageId() != null) {
                profileImageUrl = s3Service.createPresignedGetUrl(
                        s3Buckets.getCustomer(), "profile-images/%s/%s".formatted(player.getId(), player.getProfileImageId()));
            }
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
                    .profileImageUrl(profileImageUrl)
                    .isEmailVerified(player.getIsEmailVerified())
                    .build();
        }
        return null;
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void uploadPlayerProfileImage(MultipartFile file, Authentication authentication) {
        PlayerProfileResponseDto profileByEmail = getPlayerProfileByEmail(authentication.getName());
        String profileImageId = UUID.randomUUID().toString();

        String existingProfileImageId = profileByEmail.profileImageId();
        try {
            // Delete the old file in the S3 bucket if it exists
            if (existingProfileImageId != null && !existingProfileImageId.isEmpty()) {
                s3Service.deleteObject(s3Buckets.getCustomer(),
                        "profile-images/%s/%s".formatted(profileByEmail.id(), existingProfileImageId));
            }

            s3Service.putObject(s3Buckets.getCustomer(),
                    "profile-images/%s/%s".formatted(profileByEmail.id(), profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updatePlayerProfileImageId(profileImageId, profileByEmail.email());
    }


    public void updatePlayerProfileImageId(String imageId, String playerEmail) {
        Player player = playerRepository.findOptionalByEmail(playerEmail).orElseThrow();
        player.setProfileImageId(imageId);
        playerRepository.save(player);
    }
}
