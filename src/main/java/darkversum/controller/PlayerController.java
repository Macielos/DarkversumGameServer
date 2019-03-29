package darkversum.controller;

import darkversum.dto.MatchDto;
import darkversum.dto.PlayerDto;
import darkversum.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;


@RestController
@RequestMapping("/player")
@PreAuthorize("hasRole('USER')")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @CrossOrigin
    @GetMapping
    public Flux<PlayerDto> getAll() {
        return playerService.getAll();
    }

    @CrossOrigin
    @GetMapping(params = "id")
    public Mono<PlayerDto> getById(BigInteger id) {
        return playerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PlayerDto> register(@RequestBody PlayerDto playerDto) {
        return playerService.register(playerDto);
    }
}
