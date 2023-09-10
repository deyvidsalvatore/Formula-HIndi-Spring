package com.deyvidsalvatore.formula.msraces.domain.race.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarPosition {

    private Car car;
    private int position;

    public void setPosition(int newPosition) {
        position = newPosition;
    }
}
