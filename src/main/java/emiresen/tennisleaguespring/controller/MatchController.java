package emiresen.tennisleaguespring.controller;

import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.dtos.response.MatchResponseDto;
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

    @PostMapping("/save")
    public ResponseEntity<String> saveNewMatch(@RequestBody @Valid SaveNewMatchRequestDto newMatchDto, Authentication authentication) {
        matchService.saveNewMatch(newMatchDto);
        return ResponseEntity.ok("Match saved successfully");
    }


    @GetMapping("/matches")
    public ResponseEntity<List<MatchResponseDto>> getMatches() {
        return ResponseEntity.ok(matchService.findAll());

    }
}

