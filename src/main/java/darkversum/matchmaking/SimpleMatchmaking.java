package darkversum.matchmaking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@Profile("Windows")
@Slf4j
public class SimpleMatchmaking implements Matchmaking {

	private static final List<PlayerInMatchIds> EMPTY = Collections.emptyList();

	private Map<String, List<PlayerInMatchIds>> playerIdsByRegionAndLevel = new HashMap<>();

	@Override
	public Flux<PlayerInMatchIds> get(String key) {
		return Flux.fromIterable(playerIdsByRegionAndLevel.getOrDefault(key, EMPTY));
	}

	@Override
	public Flux<PlayerInMatchIds> joinAndGetIfReady(String key, PlayerInMatchIds player, int totalPlayerCount) {
		log.info("RemoveIfNumberSufficient: {}, {}, {}", key, totalPlayerCount, playerIdsByRegionAndLevel);
		List<PlayerInMatchIds> playerIds = playerIdsByRegionAndLevel.get(key);
		if(playerIds == null) {
			playerIdsByRegionAndLevel.put(key, playerIds = new ArrayList<>());
		}
		synchronized (playerIds) {
			if(!playerIds.contains(player)) {
				playerIds.add(player);
			}
			if (playerIds.size() < totalPlayerCount) {
				return Flux.fromIterable(playerIds);
			}
			playerIdsByRegionAndLevel.put(key, new ArrayList<>(playerIds.subList(totalPlayerCount, playerIds.size())));
			return Flux.fromIterable(playerIds.subList(0, totalPlayerCount));
		}
	}
}