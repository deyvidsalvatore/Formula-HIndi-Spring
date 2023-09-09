package deyvid.silva.formula.mscars.domain.car.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import deyvid.silva.formula.mscars.domain.car.dto.CarRequest;
import deyvid.silva.formula.mscars.domain.car.dto.CarResponse;
import deyvid.silva.formula.mscars.domain.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @Test
    void testGetAllCars() throws Exception {
        List<CarResponse> cars = new ArrayList<>();
        cars.add(new CarResponse());

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAllCarsPage() throws Exception {

        List<CarResponse> carResponses = new ArrayList<>();
        carResponses.add(new CarResponse());
        carResponses.add(new CarResponse());
        Page<CarResponse> carResponsePage = new PageImpl<>(carResponses);

        Mockito.when(carService.getAllCarsPage(0, 10)).thenReturn(carResponsePage);

        mockMvc.perform(get("/api/v1/cars/filter?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].brand", is(nullValue())))
                .andExpect(jsonPath("$.content[1].brand", is(nullValue())))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)));
    }

    @Test
    void testGetCarById() throws Exception {
        UUID carId = UUID.randomUUID();
        CarResponse car = new CarResponse();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/api/v1/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testCreateNewCar() throws Exception {
        CarRequest request = new CarRequest("Ford", "Mustang", null, "2023");
        CarResponse response = new CarResponse();

        when(carService.createNewCar(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testUpdateExistingCar() throws Exception {
        UUID carId = UUID.randomUUID();
        CarRequest request = new CarRequest("Honda", "Civic", null, "2024");
        CarResponse response = new CarResponse();

        when(carService.updateExistingCar(carId, request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testDeleteExistingCar() throws Exception {
        UUID carId = UUID.randomUUID();
        String deleteMessage = "Car deleted successfully";
        when(carService.deleteExistingCarById(carId)).thenReturn(deleteMessage);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(deleteMessage));
    }
}
