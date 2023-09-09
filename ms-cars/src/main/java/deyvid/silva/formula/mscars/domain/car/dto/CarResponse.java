package deyvid.silva.formula.mscars.domain.car.dto;

import deyvid.silva.formula.mscars.domain.car.model.Pilot;
import lombok.Data;

import java.util.UUID;

@Data
public class CarResponse {
    private UUID id;
    private String brand;
    private String model;
    private Pilot pilot;
    private String year;
}
