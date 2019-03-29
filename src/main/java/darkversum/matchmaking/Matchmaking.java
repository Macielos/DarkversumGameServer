package darkversum.matchmaking;

import reactor.core.publisher.Flux;

public interface Matchmaking {

	Flux<PlayerInMatchIds> get(String key);

	Flux<PlayerInMatchIds> joinAndGetIfReady(String key, PlayerInMatchIds playerInMatchIds, int totalPlayerCount);

}