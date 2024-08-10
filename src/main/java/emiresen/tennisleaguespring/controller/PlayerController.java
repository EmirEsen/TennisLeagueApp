package emiresen.tennisleaguespring.controller;
import emiresen.tennisleaguespring.dtos.request.PlayerProfileUpdateDto;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/profiles")
    public ResponseEntity<List<PlayerProfileResponseDto>> getPlayerProfiles(){
        return ResponseEntity.ok(playerService.findAll());
    }

    @GetMapping("/profile")
    public ResponseEntity<PlayerProfileResponseDto> getPlayerProfile(){
        PlayerProfileResponseDto playerProfileByEmail = playerService
                .getPlayerProfileByEmail(getAuthenticatedUserEmail());
        System.out.println(playerProfileByEmail);
        return ResponseEntity.ok(playerProfileByEmail);
    }

    @PutMapping("/profile/update")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<PlayerProfileResponseDto> updatePlayerProfile(
            @RequestBody PlayerProfileUpdateDto playerProfileUpdateDto,
            Authentication authentication
            ){
        PlayerProfileResponseDto updateProfile = playerService.update(playerProfileUpdateDto);
        System.out.println(updateProfile);
        return ResponseEntity.ok(playerService.getPlayerProfileByEmail(getAuthenticatedUserEmail()));
    }

    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER')")
    public void uploadPlayerProfileImage(@RequestParam("file") MultipartFile file, Authentication authentication){
        System.out.println("File name: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize() + " bytes");
        System.out.println("File type: " + file.getContentType());
        playerService.uploadPlayerProfileImage(file, authentication);
    }

    private String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername(); // Assuming getUsername() returns the email
            }
        }
        return null;
    }
}
