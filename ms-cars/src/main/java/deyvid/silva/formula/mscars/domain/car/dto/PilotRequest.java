package deyvid.silva.formula.mscars.domain.car.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PilotRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "pilot's name cannot be blank")
    @NotNull(message = "pilot's name cannot be null")
    private String name;

    @NotNull(message = "pilot's age cannot be null")
    @Positive(message = "pilot's age must be positive")
    private Integer age;
}