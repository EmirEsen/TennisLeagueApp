package emiresen.tennisleaguespring.service;


import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerLoginRequestDto;
import emiresen.tennisleaguespring.dataTransfer.dtos.request.PlayerRegisterRequestDto;
import emiresen.tennisleaguespring.document.Player;
import emiresen.tennisleaguespring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public boolean register(PlayerRegisterRequestDto dto){
        if(playerRepository.existsByEmail(dto.getEmail())){
            return false;
        }
        playerRepository.save(Player.builder()
                        .firstname(dto.getFirstname())
                        .lastname(dto.getLastname())
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                .build());
        return true;
    }


    public Optional<Player> login(PlayerLoginRequestDto dto) {
        boolean isValidEmail = playerRepository.existsByEmail(dto.getEmail());
        boolean isValidPassword = passwordEncoder.matches(dto.getPassword(), dto.getPassword());
        if(isValidEmail && isValidPassword){
            return playerRepository.findOptionalByEmail(dto.getEmail());
        }
        return Optional.empty();
    }



    public List<Player> findAll() {
        return playerRepository.findAll();
    }



}
