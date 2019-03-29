package darkversum.persistentrepository;

import darkversum.model.Faction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface FactionRepository extends ReactiveMongoRepository<Faction, BigInteger> {

	Mono<Faction> findByName(String name);
}