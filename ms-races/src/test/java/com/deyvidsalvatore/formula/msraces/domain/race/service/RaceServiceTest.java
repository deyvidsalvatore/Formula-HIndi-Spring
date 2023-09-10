package com.deyvidsalvatore.formula.msraces.domain.race.service;

import com.deyvidsalvatore.formula.msraces.domain.race.client.CarsFeignClient;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Car;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Race;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RaceServiceTest {

    private RaceService raceService;
    private CarsFeignClient carsFeignClient;

    @BeforeEach
    public void setUp() {
        carsFeignClient = mock(CarsFeignClient.class);
        raceService = new RaceService(carsFeignClient);
    }

    @Test
    void testStartRaceWithValidInput() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(UUID.randomUUID(), "Ford", "Mustang", null, "2018"));
        cars.add(new Car(UUID.randomUUID(), "Honda", "Civic", null, "2016"));
        cars.add(new Car(UUID.randomUUID(), "Chevrolet", "Malibu", null, "2015"));

        when(carsFeignClient.getAllCars()).thenReturn(cars);

        Track track = new Track("Track", "Brazil", "2020-10-12");

        Race race = raceService.startRace(track);

        assertEquals(3, race.getCars().size());
        assertEquals("Brazil", race.getTrack().getCountry());
        assertEquals("2020-10-12", race.getTrack().getDate());
    }

    @Test
    void testStartRaceWithInvalidCountry() {
        Track track = new Track("Track", "InvalidCountry", "2020-10-12");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> raceService.startRace(track));

        assertEquals("Invalid country name.", exception.getMessage());
    }

    @Test
    void testStartRaceWithInvalidDate() {
        Track track = new Track("Track", "Brazil", "2020/10/12");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> raceService.startRace(track));

        assertEquals("Invalid date format. Please use yyyy-MM-dd.", exception.getMessage());
    }

    @Test
    void testStartRaceWithNotEnoughCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(UUID.randomUUID(), "Ford", "Mustang", null, "2018"));
        cars.add(new Car(UUID.randomUUID(), "Honda", "Civic", null, "2016"));

        when(carsFeignClient.getAllCars()).thenReturn(cars);

        Track track = new Track("Track", "Brazil", "2020-10-12");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> raceService.startRace(track));

        assertEquals("There are not enough cars to start the race.", exception.getMessage());
    }

}
