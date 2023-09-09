package deyvid.silva.formula.mscars.domain.car.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError {
    private LocalDateTime timestamp;
    private String error;
    private Integer status;
    private String path;
}

