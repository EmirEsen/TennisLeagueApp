package emiresen.tennisleaguespring.controller;


import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dataTransfer.dtos.response.AuthenticationResponse;
import emiresen.tennisleaguespring.dataTransfer.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final PlayerService playerService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerNewPlayer(@RequestBody PlayerRegisterRequestDto dto){
        AuthenticationResponse registeredPlayer = playerService.register(dto);
        return ResponseEntity.ok(registeredPlayer);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody PlayerLoginRequestDto dto){
        AuthenticationResponse loggedInPlayer = playerService.login(dto);
        return ResponseEntity.ok(loggedInPlayer);
    }

}
