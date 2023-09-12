package deyvid.silva.formula.mscars.domain.car.controller;

import deyvid.silva.formula.mscars.domain.car.dto.CarRequest;
import deyvid.silva.formula.mscars.domain.car.dto.CarResponse;
import deyvid.silva.formula.mscars.domain.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cars")
@Validated
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllCars());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<CarResponse>> getAllCarsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllCarsPage(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<CarResponse> createNewCar(@Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createNewCar(carRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateExistingCar(@PathVariable UUID id, @Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.updateExistingCar(id, carRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExistingCar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.deleteExistingCarById(id));
    }
}
