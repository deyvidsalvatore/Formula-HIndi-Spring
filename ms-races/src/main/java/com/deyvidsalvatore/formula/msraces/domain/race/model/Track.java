package com.deyvidsalvatore.formula.msraces.domain.race.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @NotNull(message = "Track Name cannot be Null")
    @NotBlank(message = "Track Name cannot be Blank")
    @Size(min = 5, max = 50, message = "Track Name to be between 5 to 40 characters")
    private String name;

    @NotNull(message = "Track Country cannot be Null")
    @NotBlank(message = "Track Country cannot be Blank")
    private String country;

    @NotNull(message = "Date cannot be null")
    @NotBlank(message = "Date cannot be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Please use yyyy/MM/dd.")
    private String date;

}
