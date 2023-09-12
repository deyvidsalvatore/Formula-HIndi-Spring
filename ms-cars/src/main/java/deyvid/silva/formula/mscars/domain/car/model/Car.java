package deyvid.silva.formula.mscars.domain.car.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Document(collection = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String brand;
    private String model;
    private Pilot pilot;
    private String year;
}