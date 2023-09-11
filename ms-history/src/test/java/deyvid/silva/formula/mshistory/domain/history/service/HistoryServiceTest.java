package deyvid.silva.formula.mshistory.domain.history.service;

import deyvid.silva.formula.mshistory.domain.history.model.Race;
import deyvid.silva.formula.mshistory.domain.history.repository.RaceRepository;
import deyvid.silva.formula.mshistory.domain.history.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class HistoryServiceTest {

    private HistoryService historyService;

    @Mock
    private RaceRepository raceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        historyService = new HistoryService(raceRepository);
    }

    @Test
    void testGetRaceByIdWithValidId() {
        UUID raceId = UUID.randomUUID();
        Race race = new Race();
        race.setId(raceId);
        when(raceRepository.findById(raceId)).thenReturn(Optional.of(race));

        Race result = historyService.getRaceById(raceId);

        assertEquals(raceId, result.getId());
    }

    @Test
    void testGetRaceByIdWithInvalidId() {
        UUID raceId = UUID.randomUUID();
        when(raceRepository.findById(raceId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> historyService.getRaceById(raceId));
    }

    @Test
    void testGetAllRacesWithRaces() {
        List<Race> races = new ArrayList<>();
        races.add(new Race());
        races.add(new Race());
        when(raceRepository.findAll()).thenReturn(races);

        List<Race> result = historyService.getAllRaces();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllRacesWithNoRaces() {
        when(raceRepository.findAll()).thenReturn(new ArrayList<>());

        List<Race> result = historyService.getAllRaces();

        assertTrue(result.isEmpty());
    }
}