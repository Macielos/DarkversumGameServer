package darkversum.controller;

import darkversum.dto.MatchDto;
import darkversum.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Objects;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService gameService;

    @CrossOrigin
    @GetMapping(params = "id")
    public Mono<MatchDto> getById(BigInteger id) {
        return gameService.getById(id);
    }

    @CrossOrigin
    @GetMapping
    public Flux<MatchDto> getAll() {
        return gameService.getAll();
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MatchDto> start(@RequestBody MatchDto game) {
        Objects.requireNonNull(game, "Game can't be null");
        return gameService.start(game);
    }

    @PutMapping("/resolve")
    public Mono<MatchDto> resolve(@RequestBody MatchDto game) {
        Objects.requireNonNull(game, "Game can't be null");
        return gameService.resolve(game);
    }

    @GetMapping(value = "/findMatch")
    public Mono<MatchDto> findMatch(@RequestParam String player, @RequestParam String faction, @RequestParam Integer players) {
        Objects.requireNonNull(player, "Player can't be null");
        Objects.requireNonNull(faction, "Faction can't be null");
        Objects.requireNonNull(players, "Players can't be null");

        return gameService.findMatch(player, faction, players);
    }
}
