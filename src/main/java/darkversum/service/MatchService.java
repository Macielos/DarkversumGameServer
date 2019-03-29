package darkversum.service;

import darkversum.dto.MatchDto;
import darkversum.dto.PlayerInMatchDto;
import darkversum.matchmaking.Matchmaking;
import darkversum.matchmaking.PlayerInMatchIds;
import darkversum.model.*;
import darkversum.persistentrepository.FactionRepository;
import darkversum.persistentrepository.MatchRepository;
import darkversum.persistentrepository.PlayerRepository;
import darkversum.persistentrepository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class MatchService {

    private static final int LEVEL_BUCKET_SIZE = 5;

    @Autowired
    private Matchmaking matchmaking;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private FactionRepository factionRepository;
    @Autowired
    private RegionRepository regionRepository;

    private Map<BigInteger, Faction> factionsById;
    private Map<String, Faction> factionsByName;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    private void init() {
        executor.execute(this::initCacheAndData);
    }

    private void initCacheAndData() {
//        try {
//            Thread.sleep(5000L);
//        } catch (InterruptedException e) {
//            log.error("Interrupted", e);
//            return;
//        }
        if (factionRepository.count().blockOptional().orElse(0L) == 0) {
            log.info("Inserting factions...");
            factionRepository.saveAll(Stream.of(
                    new Faction(BigInteger.valueOf(1L), "OWDI"),
                    new Faction(BigInteger.valueOf(2L), "TH"),
                    new Faction(BigInteger.valueOf(3L), "VH"))
                    .collect(Collectors.toList())).blockLast();
        }
        if (regionRepository.count().blockOptional().orElse(0L) == 0) {
            log.info("Inserting regions...");
            regionRepository.saveAll(Stream.of(
                    new Region(BigInteger.valueOf(1L), "Poland"),
                    new Region(BigInteger.valueOf(2L), "UK"),
                    new Region(BigInteger.valueOf(3L), "USA"))
                    .collect(Collectors.toList())).blockLast();
        }

        log.info(factionRepository.count().blockOptional().orElse(0L) + " factions in db");
        log.info(regionRepository.count().blockOptional().orElse(0L) + " regions in db");

        log.info("Creating caches...");
        factionsById = factionRepository.findAll().collectMap(Faction::getId).block();
        factionsByName = factionsById.values().stream().collect(Collectors.toMap(Faction::getName, Function.identity()));
        log.info("Caches created");
    }

    public Mono<MatchDto> findMatch(String playerName, String factionName, int totalPlayerCount) {
        log.info("findMatch: {}", playerName);
        Faction faction = factionsByName.get(factionName);
        //this was the moment I stopped liking reactive programming...
        return playerRepository.findByName(playerName)
                .flatMapMany(player -> matchmaking.joinAndGetIfReady(getKey(player, totalPlayerCount), new PlayerInMatchIds(player.getId(), faction.getId()), totalPlayerCount))
                .flatMap(playerIds -> playerRepository.findById(playerIds.getPlayerId())
                        .map(player -> new PlayerInMatch(player, factionsById.get(playerIds.getFactionId()))))
                .collectList()
                .map(playersInMatch -> {
                    MatchDto.Status status = playersInMatch.size() == totalPlayerCount ? MatchDto.Status.READY : MatchDto.Status.WAITING_FOR_PLAYERS;
                    Match match = new Match(null, status, playersInMatch);
                    log.info("Match: {}", match);
                    return match;
                })
                .flatMap(match -> match.getStatus() == MatchDto.Status.READY ? matchRepository.save(match) : Mono.just(match))
                .map(this::toDto)
                .doOnError(e -> log.error("Matchmaking failed: ", e));
    }

    public Mono<MatchDto> getById(BigInteger id) {
        log.info("getById: {}", id);
        return matchRepository.findById(id).map(this::toDto);
    }

    public Flux<MatchDto> getAll() {
        log.info("getAll");
        return matchRepository.findAll().map(this::toDto);
    }

    public Mono<MatchDto> start(MatchDto game) {
        log.info("Starting game: {}", game);
        game.setStatus(MatchDto.Status.STARTED);
        return convertAndSave(game);
    }

    public Mono<MatchDto> resolve(MatchDto game) {
        log.info("Resolving game: {}", game);
        game.setStatus(MatchDto.Status.RESOLVED);
        return convertAndSave(game);
    }

    private Mono<MatchDto> convertAndSave(MatchDto game) {
        return toDocument(game)
                .flatMap(matchRepository::save)
                .map(this::toDto);
    }

    private MatchDto toDto(Match match) {
        return new MatchDto(match.getId().toString(), match.getStatus(), match.getPlayers().stream()
                .map(playerInMatch -> new PlayerInMatchDto(playerInMatch.getPlayer().getName(), playerInMatch.getFaction().getName()))
                .collect(Collectors.toList()), match.getCreatedDate(), match.getUpdatedDate());
    }

    private Mono<Match> toDocument(MatchDto matchDto) {
        return Flux.fromIterable(matchDto.getPlayers())
                .map(playerInMatchDto -> playerRepository.findByName(playerInMatchDto.getPlayerName())
                        .zipWith(Mono.just(factionsByName.get(playerInMatchDto.getFactionName()))))
                .flatMap(tuple2Mono -> tuple2Mono.map(tuple -> new PlayerInMatch(tuple.getT1(), tuple.getT2())))
                .collectList()
                .map(players -> new Match(new BigInteger(matchDto.getId()), matchDto.getStatus(), players, matchDto.getCreatedDate(), matchDto.getUpdatedDate()));
    }

    private String getKey(Player player, int totalPlayersCount) {
        return player.getRegion().getId()+"_"+(player.getLevel() / LEVEL_BUCKET_SIZE)+"_"+totalPlayersCount;
    }

}
