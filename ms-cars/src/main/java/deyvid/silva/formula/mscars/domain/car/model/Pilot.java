package deyvid.silva.formula.mscars.domain.car.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pilot implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;
}
