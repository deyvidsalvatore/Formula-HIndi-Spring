package com.deyvidsalvatore.formula.msraces.domain.race.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private UUID id;
    private String brand;
    private String model;
    private Pilot pilot;
    private String year;
}
