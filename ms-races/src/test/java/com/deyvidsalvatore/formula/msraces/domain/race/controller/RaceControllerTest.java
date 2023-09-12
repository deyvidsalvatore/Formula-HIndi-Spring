package com.deyvidsalvatore.formula.msraces.domain.race.controller;

import com.deyvidsalvatore.formula.msraces.domain.race.model.Race;
import com.deyvidsalvatore.formula.msraces.domain.race.model.Track;
import com.deyvidsalvatore.formula.msraces.domain.race.service.RaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceController.class)
class RaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceService raceService;

    @Test
    void testStartRaceWithValidInput() throws Exception {
        Race race = new Race();
        race.setTrack(new Track("Track", "Brazil", "2020-10-12"));

        Mockito.when(raceService.startRace(Mockito.any(Track.class))).thenReturn(race);

        Track trackRequest = new Track("Track", "Brazil", "2020-10-12");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.track.country").value("Brazil"))
                .andExpect(jsonPath("$.track.date").value("2020-10-12"));
    }

    @Test
    void testStartRaceWithInvalidCountry() throws Exception {
        Track trackRequest = new Track("Track", null, "2020-10-12");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testStartRaceWithInvalidDate() throws Exception {
        Track trackRequest = new Track("Track", "Brazil", "2020/10/12");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequest)))
                .andExpect(status().isBadRequest());
    }
}