package deyvid.silva.formula.mscars.domain.car.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "brand cannot be blank")
    @NotNull(message = "brand cannot be null")
    @Size(max = 20, message = "brand can have a maximum of 20 characters")
    private String brand;

    @NotBlank(message = "model cannot be blank")
    @NotNull(message = "model cannot be null")
    @Size(max = 40, message = "model can have a maximum of 40 characters")
    private String model;

    @Valid
    private PilotRequest pilot;

    @NotNull(message = "year cannot be null")
    @NotBlank(message = "year cannot be blank")
    @Size(max = 4, message = "year maximum 4 characters")
    private String year;


}
