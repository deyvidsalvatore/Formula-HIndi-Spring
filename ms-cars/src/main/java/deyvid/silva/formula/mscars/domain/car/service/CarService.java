package deyvid.silva.formula.mscars.domain.car.service;

import deyvid.silva.formula.mscars.domain.car.dto.CarRequest;
import deyvid.silva.formula.mscars.domain.car.dto.CarResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarResponse> getAllCars();
    Page<CarResponse> getAllCarsPage(int page, int sizeP);
    CarResponse getCarById(UUID id);
    CarResponse createNewCar(CarRequest carRequest);
    CarResponse updateExistingCar(UUID id, CarRequest carRequest);
    String deleteExistingCarById(UUID id);
}
