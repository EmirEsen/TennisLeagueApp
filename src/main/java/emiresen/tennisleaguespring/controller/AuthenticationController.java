package emiresen.tennisleaguespring.controller;


import emiresen.tennisleaguespring.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dtos.request.PlayerSendConfirmationEmailRequest;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.exception.TennisLeagueAppException;
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

    @PostMapping("/send-confirmation-email")
    public ResponseEntity<ResponseDto<String>> resendConfirmationEmail(@RequestBody PlayerSendConfirmationEmailRequest dto) {
        System.out.println(dto.email());
        authService.resendConfirmationEmail(dto.email());
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .code(200)
                .data("Email Sent")
                .message("Confirmation Email Sent")
                .build());
    }

    //todo login after confirmation
    @GetMapping("/verify-email")
    public ResponseEntity<ResponseDto<String>> verifyEmail(@RequestParam("token") String token) throws TennisLeagueAppException {
        try{
            if(authService.verifyPlayerEmail(token)){
                return ResponseEntity.ok(ResponseDto.<String>builder()
                                .code(200)
                                .data("Email verified")
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
