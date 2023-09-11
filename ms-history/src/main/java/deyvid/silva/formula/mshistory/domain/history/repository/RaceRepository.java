package deyvid.silva.formula.mshistory.domain.history.repository;

import deyvid.silva.formula.mshistory.domain.history.model.Race;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RaceRepository extends MongoRepository<Race, UUID> {
}
