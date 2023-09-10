package com.deyvidsalvatore.formula.msraces.domain.race.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    private UUID id;
    private List<Car> cars;
    private Car winner;
    private Track track;
}
