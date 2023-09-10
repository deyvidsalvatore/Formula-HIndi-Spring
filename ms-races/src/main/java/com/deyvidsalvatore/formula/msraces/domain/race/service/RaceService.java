package com.deyvidsalvatore.formula.msraces.domain.race.service;

import com.deyvidsalvatore.formula.msraces.domain.race.client.CarsFeignClient;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Car;
import com.deyvidsalvatore.formula.msraces.domain.race.model.CarPosition;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Race;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class RaceService {

    private final CarsFeignClient carsFeignClient;

    public RaceService(CarsFeignClient carsFeignClient) {
        this.carsFeignClient = carsFeignClient;
    }

    public Race startRace(Track trackRequest) {
        if (!isValidCountry(trackRequest.getCountry())) {
            log.info("RaceService ::: startRace Invalid Country Name {}", trackRequest.getCountry());
            throw new IllegalArgumentException("Invalid country name.");
        }
        if (!isValidDate(trackRequest.getDate())) {
            log.info("RaceService ::: startRace Invalid Date {}", trackRequest.getDate());
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        List<Car> allCars = carsFeignClient.getAllCars();

        if (allCars.size() < 3) {
            log.info("RaceService ::: startRace Not Enough Cars {}", allCars.size());
            throw new IllegalStateException("There are not enough cars to start the race.");
        }

        int numberOfCarsToUse = ThreadLocalRandom.current().nextInt(3, Math.min(10, allCars.size()) + 1);
        List<Car> selectedCars = new ArrayList<>(allCars.subList(0, numberOfCarsToUse));

        Collections.shuffle(selectedCars);
        List<CarPosition> carPositions = new ArrayList<>();
        for (int i = 0; i < selectedCars.size(); i++) {
            Car car = selectedCars.get(i);
            carPositions.add(new CarPosition(car, i + 1));
        }

        log.info("Initial positions of the cars:");
        for (CarPosition carPosition : carPositions) {
            log.info("Car {} - {} {} (Initial Position: {})",
                    carPosition.getPosition(),
                    carPosition.getCar().getBrand(),
                    carPosition.getCar().getModel(),
                    carPosition.getPosition());
        }

        simulateRace(carPositions);

        Car winner = determineWinner(carPositions);

        Race race = new Race();
        race.setCars(selectedCars);
        race.setWinner(winner);
        race.setTrack(new Track(trackRequest.getName(), trackRequest.getCountry(), trackRequest.getDate()));

        return race;
    }

    private void simulateRace(List<CarPosition> carPositions) {
        long startTime = System.currentTimeMillis();

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime >= 500) {
                break;
            }

            List<CarPosition> shuffledCarPositions = new ArrayList<>(carPositions);
            Collections.shuffle(shuffledCarPositions);

            for (int i = 0; i < shuffledCarPositions.size() - 1; i++) {
                CarPosition currentCar = shuffledCarPositions.get(i);
                CarPosition nextCar = shuffledCarPositions.get(i + 1);

                if (currentCar.getPosition() > nextCar.getPosition()) {
                    int currentPosition = currentCar.getPosition();
                    currentCar.setPosition(nextCar.getPosition());
                    nextCar.setPosition(currentPosition);
                }
            }
        }

        Car winner = determineWinner(carPositions);
        log.info("Winner: {} {}", winner.getBrand(), winner.getModel());
    }

    private Car determineWinner(List<CarPosition> carPositions) {
        carPositions.sort(Comparator.comparing(CarPosition::getPosition));
        return carPositions.get(0).getCar();
    }

    private boolean isValidCountry(String countryName) {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (locale.getDisplayCountry(Locale.ENGLISH).equalsIgnoreCase(countryName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}

