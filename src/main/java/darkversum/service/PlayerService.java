package darkversum.service;

import darkversum.dto.PlayerDto;
import darkversum.model.Player;
import darkversum.persistentrepository.PlayerRepository;
import darkversum.persistentrepository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Slf4j
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RegionRepository regionRepository;

    public Mono<PlayerDto> getById(BigInteger gameId) {
        log.info("getById: {}", gameId);
        return playerRepository.findById(gameId).map(this::toDto);
    }

    public Flux<PlayerDto> getAll() {
        log.info("getAll");
        return playerRepository.findAll().map(this::toDto);
    }

    public Mono<PlayerDto> register(PlayerDto playerDto) {
        log.info("Registering player: {}", playerDto);
        return toDocument(playerDto)
                .flatMap(playerRepository::save)
                .map(this::toDto);
    }

    private PlayerDto toDto(Player player) {
        return new PlayerDto(player.getId().toString(), player.getName(), player.getRegion().getName(), player.getStatus(), player.getCreatedDate(), player.getUpdatedDate());
    }

    private Mono<Player> toDocument(PlayerDto playerDto) {
        return regionRepository.findByName(playerDto.getRegion())
                .map(region -> new Player(new BigInteger(playerDto.getId()), playerDto.getName(), region, playerDto.getStatus(), playerDto.getCreatedDate(), playerDto.getUpdatedDate()));
    }

}
