package deyvid.silva.formula.mscars.domain.car.repository;

import deyvid.silva.formula.mscars.domain.car.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends MongoRepository<Car, UUID> {
    @Query("{}")
    Page<Car> findAllCar(Pageable pageable);
    boolean existsByBrandAndModelAndYear(String brand, String model, String year);

    boolean existsByPilotNameAndPilotAge(String name, Integer age);

    List<Car> findByBrandAndModelAndPilotNameAndPilotAge(String brand, String model, String name, Integer age);
}
