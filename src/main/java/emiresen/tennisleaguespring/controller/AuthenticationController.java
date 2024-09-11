package emiresen.tennisleaguespring.controller;


import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.exception.ErrorType;
import emiresen.tennisleaguespring.exception.TennisLeagueAppException;
import emiresen.tennisleaguespring.service.AuthService;
import emiresen.tennisleaguespring.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final JwtService jwtService;

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

    //todo login after confirmation
    @GetMapping("/confirm-email")
    public ResponseEntity<ResponseDto<String>> confirmEmail(@RequestParam("token") String token) throws TennisLeagueAppException {
        try{
            if(authService.verifyPlayerEmail(token)){
                return ResponseEntity.ok(ResponseDto.<String>builder()
                                .code(200)
                                .data("Email has been verified!")
                                .message("Email verified")
                        .build());
            } else {
                return ResponseEntity.ok(ResponseDto.<String>builder()
                                .code(400)
                                .data("Email has not been verified!")
                                .message("Link expired or email already verified.")
                        .build());
            }
        } catch (TennisLeagueAppException e){
            return ResponseEntity.ok(ResponseDto.<String>builder()
                    .code(400)
                    .data("Email has not been verified!")
                    .message("Link expired or email already verified.")
                    .build());
        }
    }

}
