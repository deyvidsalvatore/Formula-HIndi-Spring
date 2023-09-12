package deyvid.silva.formula.mshistory.domain.history.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "race_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    private UUID id;
    private List<Car> cars;
    private Car winner;
    private Track track;
    private LocalDateTime timestamp;
}
