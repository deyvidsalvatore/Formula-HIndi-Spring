package deyvid.silva.formula.mscars.domain.car.service;

import deyvid.silva.formula.mscars.domain.car.dto.CarRequest;
import deyvid.silva.formula.mscars.domain.car.dto.CarResponse;
import deyvid.silva.formula.mscars.domain.car.dto.PilotRequest;
import deyvid.silva.formula.mscars.domain.car.model.Car;
import deyvid.silva.formula.mscars.domain.car.model.Pilot;
import deyvid.silva.formula.mscars.domain.car.repository.CarRepository;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicateCarException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicatePilotException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.ResourceNotFoundException;
import deyvid.silva.formula.mscars.domain.car.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(createDummyCar());
        when(carRepository.findAll()).thenReturn(cars);

        List<CarResponse> carResponses = carService.getAllCars();

        assertEquals(1, carResponses.size());
    }

    @Test
    void getCarById() {
        UUID carId = UUID.randomUUID();
        Car car = createDummyCar();
        car.setId(carId);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        CarResponse carResponseMock = new CarResponse();
        when(modelMapper.map(any(Car.class), eq(CarResponse.class))).thenReturn(carResponseMock);

        CarResponse carResponse = carService.getCarById(carId);

        assertNotNull(carResponse);
        assertEquals(carResponseMock, carResponse);
    }


    @Test
    void createNewCar() {
        CarRequest carRequest = createDummyCarRequest();
        Car car = createDummyCar();
        when(carRepository.existsByBrandAndModelAndYear(any(), any(), any())).thenReturn(false);
        when(carRepository.existsByPilotNameAndPilotAge(any(), any())).thenReturn(false);
        when(carRepository.save(any())).thenReturn(car);

        CarResponse carResponseMock = new CarResponse();
        when(modelMapper.map(any(Car.class), eq(CarResponse.class))).thenReturn(carResponseMock);

        CarResponse carResponse = carService.createNewCar(carRequest);

        assertNotNull(carResponse);
    }

    @Test
    void createNewCar_DuplicateCar() {
        CarRequest carRequest = createDummyCarRequest();
        when(carRepository.existsByBrandAndModelAndYear(any(), any(), any())).thenReturn(true);

        assertThrows(DuplicateCarException.class, () -> carService.createNewCar(carRequest));
    }

    @Test
    void createNewCar_DuplicatePilot() {
        CarRequest carRequest = createDummyCarRequest();
        when(carRepository.existsByBrandAndModelAndYear(any(), any(), any())).thenReturn(false);
        when(carRepository.existsByPilotNameAndPilotAge(any(), any())).thenReturn(true);

        assertThrows(DuplicatePilotException.class, () -> carService.createNewCar(carRequest));
    }

    @Test
    void updateExistingCar_NotFound() {
        UUID carId = UUID.randomUUID();
        CarRequest carRequest = createDummyCarRequest();
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> carService.updateExistingCar(carId, carRequest));
    }

    @Test
    void deleteExistingCarById() {
        UUID carId = UUID.randomUUID();
        Car car = createDummyCar();
        car.setId(carId);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        String result = carService.deleteExistingCarById(carId);

        assertEquals("Car deleted successfully", result);
        verify(carRepository, times(1)).delete(car);
    }

    private Car createDummyCar() {
        return Car.builder()
                .id(UUID.randomUUID())
                .brand("Car Brand")
                .model("Car Model")
                .pilot(Pilot.builder().name("Airton Senna").age(30).build())
                .year("2023")
                .build();
    }

    private CarRequest createDummyCarRequest() {
        return new CarRequest("Honda", "Civic",
                new PilotRequest("Gordão da XJ", 30), "2023");
    }
}
