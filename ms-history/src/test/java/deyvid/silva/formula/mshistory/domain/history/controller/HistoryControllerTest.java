package deyvid.silva.formula.mshistory.domain.history.controller;

import deyvid.silva.formula.mshistory.domain.history.model.Race;
import deyvid.silva.formula.mshistory.domain.history.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Test
    void testGetRaceById() throws Exception {
        UUID raceId = UUID.randomUUID();
        Race race = new Race();
        race.setId(raceId);

        Mockito.when(historyService.getRaceById(raceId)).thenReturn(race);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/history/{id}", raceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(raceId.toString()));
    }

    @Test
    void testGetAllRaces() throws Exception {
        List<Race> races = new ArrayList<>();
        races.add(new Race());
        races.add(new Race());

        Mockito.when(historyService.getAllRaces()).thenReturn(races);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(races.size()));
    }
}
