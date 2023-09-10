package com.deyvidsalvatore.formula.msraces.domain.race.controller;

import com.deyvidsalvatore.formula.msraces.domain.race.model.Race;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Track;
import com.deyvidsalvatore.formula.msraces.domain.race.service.RaceService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/races")
@Validated
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping
    public Race startRace(@Valid @RequestBody Track trackRequest) {
        return raceService.startRace(trackRequest);
    }
}
