package deyvid.silva.formula.mscars.domain.car.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import deyvid.silva.formula.mscars.domain.car.model.Car;
import deyvid.silva.formula.mscars.domain.car.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class DataInitializr implements CommandLineRunner {

    private final CarRepository carRepository;

    public DataInitializr(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) {
        try {
            ClassPathResource resource = new ClassPathResource("data.json");
            ObjectMapper objectMapper = new ObjectMapper();

            Car[] cars = objectMapper.readValue(resource.getInputStream(), Car[].class);

            Arrays.stream(cars)
                    .forEach(car -> {
                        car.setId(UUID.randomUUID());
                        saveIfNotExists(car);
                    });

        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void saveIfNotExists(Car car) {
        List<Car> existingCars = carRepository.findByBrandAndModelAndPilotNameAndPilotAge(
                car.getBrand(), car.getModel(), car.getPilot().getName(), car.getPilot().getAge()
        );

        if (existingCars.isEmpty()) {
            carRepository.save(car);
        }
    }
}
