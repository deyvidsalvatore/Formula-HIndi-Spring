package com.deyvidsalvatore.formula.msraces.domain.race.client;

import com.deyvidsalvatore.formula.msraces.domain.race.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-cars", url = "http://localhost:8000")
public interface CarsFeignClient {

    @GetMapping("/api/v1/cars")
    List<Car> getAllCars();

}
