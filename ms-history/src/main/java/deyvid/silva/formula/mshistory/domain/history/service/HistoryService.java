package deyvid.silva.formula.mshistory.domain.history.service;

import deyvid.silva.formula.mshistory.domain.history.model.Race;
import deyvid.silva.formula.mshistory.domain.history.repository.RaceRepository;
import deyvid.silva.formula.mshistory.domain.history.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HistoryService {

    private final RaceRepository raceRepository;

    public HistoryService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public Race getRaceById(UUID id) {
        return raceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Race with id: " + id + " was not found"));
    }

    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }
}
