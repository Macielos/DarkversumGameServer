package darkversum.persistentrepository;

import darkversum.model.Region;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface RegionRepository extends ReactiveMongoRepository<Region, BigInteger> {

	Mono<Region> findByName(String name);
}