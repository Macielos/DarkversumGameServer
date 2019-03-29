package darkversum.persistentrepository;

import darkversum.model.Match;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, BigInteger> {

}