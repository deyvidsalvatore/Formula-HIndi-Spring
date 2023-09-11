package deyvid.silva.formula.mshistory.domain.history.controller;

import deyvid.silva.formula.mshistory.domain.history.model.Race;
import deyvid.silva.formula.mshistory.domain.history.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(historyService.getRaceById(id));
    }

    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        return ResponseEntity.status(HttpStatus.OK).body(historyService.getAllRaces());
    }
}
