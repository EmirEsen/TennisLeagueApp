package emiresen.tennisleaguespring.controller;


import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.dataTransfer.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
//    private final Jwt


    @PostMapping("/register")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDto<Boolean>> registerNewPlayer(@RequestBody PlayerRegisterRequestDto dto){
        boolean isSaved = playerService.register(dto);
        return ResponseEntity.ok(ResponseDto.<Boolean>builder()
                .code(200)
                .message(isSaved ? "Player registered successfully" : "Email already in use!")
                .data(isSaved)
                .build());
    }

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody PlayerLoginRequestDto dto){
        Optional<Player> user = playerService.login(dto);
        if(user.isEmpty()){

        }
        String token = jwtManager.createToken(user.get().getId());
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .code(200)
                .message("Başarılı şekilde giriş yapıldı")
                .data(token)
                .build());
    }



    @GetMapping("/get-all")
    public ResponseEntity<List<Player>> getPlayers(){
        return ResponseEntity.ok(playerService.findAll());
    }
}
