package emiresen.tennisleaguespring.controller;


import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.AuthenticationResponse;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> registerNewPlayer(@Valid @RequestBody PlayerRegisterRequestDto dto) {
        ResponseDto<String> registeredPlayer = authService.register(dto);
        return ResponseEntity.ok(registeredPlayer);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody PlayerLoginRequestDto dto) {
        String loggedInPlayerToken = authService.login(dto);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .code(200)
                .data(loggedInPlayerToken)
                .message("Player logged in")
                .build());
    }

}
