package deyvid.silva.formula.mshistory.domain.history.model;

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
