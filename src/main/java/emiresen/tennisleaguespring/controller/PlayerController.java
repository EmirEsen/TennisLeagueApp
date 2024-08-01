package emiresen.tennisleaguespring.controller;
import emiresen.tennisleaguespring.dtos.response.PlayerProfileResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<PlayerProfileResponseDto> getPlayerProfile(){
        return ResponseEntity.ok(playerService.getPlayerProfileByEmail(getAuthenticatedUserEmail()));
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
