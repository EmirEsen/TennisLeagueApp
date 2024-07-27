package emiresen.tennisleaguespring.controller;

import emiresen.tennisleaguespring.dtos.request.SaveNewMatchRequestDto;
import emiresen.tennisleaguespring.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/save")
    public ResponseEntity<String> saveNewMatch(@RequestBody @Valid SaveNewMatchRequestDto newMatchDto) {
        matchService.saveNewMatch(newMatchDto);
        return ResponseEntity.ok("Match saved successfully");
    }
}

