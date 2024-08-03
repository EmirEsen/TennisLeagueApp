package emiresen.tennisleaguespring.controller;

import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.dtos.response.MatchResponseDto;
import emiresen.tennisleaguespring.dtos.response.ResponseDto;
import emiresen.tennisleaguespring.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/match")
public class MatchController {

    private final MatchService matchService;

    // todo after saving new match return new player list which will be with updated ratings
    @PostMapping("/save")
    public ResponseEntity<ResponseDto<MatchResponseDto>> saveNewMatch(@RequestBody @Valid SaveNewMatchRequestDto newMatchDto, Authentication authentication) {
        MatchResponseDto matchResponseDto = matchService.saveNewMatch(newMatchDto);
        return ResponseEntity.ok(ResponseDto.<MatchResponseDto>builder()
                        .code(200)
                        .data(matchResponseDto)
                        .message("%s match saved successfully".formatted(matchResponseDto.getId()))
                .build()
        );
    }


    @GetMapping("/matches")
    public ResponseEntity<List<MatchResponseDto>> getMatches() {
        return ResponseEntity.ok(matchService.findAll());

    }
}

