package darkversum.persistentrepository;

import darkversum.model.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, BigInteger> {

	Mono<Player> findByName(String name);
}