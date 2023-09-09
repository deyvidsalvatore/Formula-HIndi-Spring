package deyvid.silva.formula.mscars.domain.car.service.impl;

import deyvid.silva.formula.mscars.domain.car.dto.CarRequest;
import deyvid.silva.formula.mscars.domain.car.dto.CarResponse;
import deyvid.silva.formula.mscars.domain.car.model.Car;
import deyvid.silva.formula.mscars.domain.car.model.Pilot;
import deyvid.silva.formula.mscars.domain.car.repository.CarRepository;
import deyvid.silva.formula.mscars.domain.car.service.CarService;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicateCarException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicatePilotException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    private static final String CAR_NOT_FOUND_MSG = "Car was not found with ID: ";

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Override
    public Page<CarResponse> getAllCarsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> carPage = carRepository.findAllCar(pageable);
        return carPage.map(this::entityToResponse);
    }

    @Override
    public CarResponse getCarById(UUID id) {
        return entityToResponse(carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND_MSG + id)));
    }

    @Override
    public CarResponse createNewCar(CarRequest carRequest) {

        if (carRepository.existsByBrandAndModelAndYear(carRequest.getBrand(), carRequest.getModel(), carRequest.getYear())) {
            throw new DuplicateCarException("Car with the same brand, model, and year already exists.");
        }

        var pilot = Pilot.builder()
                .name(carRequest.getPilot().getName())
                .age(carRequest.getPilot().getAge())
                .build();

        if (carRepository.existsByPilotNameAndPilotAge(pilot.getName(), pilot.getAge())) {
            throw new DuplicatePilotException("Pilot with the same name and age already exists.");
        }

        var car = Car.builder()
                .id(UUID.randomUUID())
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .pilot(pilot)
                .year(carRequest.getYear())
                .build();

        return entityToResponse(carRepository.save(car));

    }

    @Override
    public CarResponse updateExistingCar(UUID id, CarRequest carRequest) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND_MSG + id));

        var updatedPilot = Pilot.builder()
                .name(carRequest.getPilot().getName())
                .age(carRequest.getPilot().getAge())
                .build();

        var updatedCar = Car.builder()
                .id(car.getId())
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .pilot(updatedPilot)
                .year(carRequest.getYear())
                .build();

        return entityToResponse(carRepository.save(updatedCar));
    }

    @Override
    public String deleteExistingCarById(UUID id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND_MSG + id));
        carRepository.delete(car);
        return "Car deleted successfully";
    }

    public CarResponse entityToResponse(Car car) {
        return modelMapper.map(car, CarResponse.class);
    }
}
