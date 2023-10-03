package com.capitole.hotel.entrypoint;

import com.capitole.hotel.entrypoint.dto.CountResponseDTO;
import com.capitole.hotel.entrypoint.dto.ReservationDTO;
import com.capitole.hotel.entrypoint.dto.SearchResponseDTO;
import com.capitole.hotel.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String hotelId;
    private String checkIn;
    private String checkOut;

    @BeforeEach
    void init() {
        hotelId = UUID.randomUUID().toString();
        checkIn = TestUtils.format(LocalDate.now());
        checkOut = TestUtils.format(LocalDate.now().plusDays(4));
    }

    @Test
    void search() throws Exception {
        String request = TestUtils.searchRequest(hotelId, checkIn, checkOut, Arrays.asList("10", "20"));
        var result = mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").isString()).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        assertThat(result.getResponse().getContentAsString()).isNotNull();
    }

    @Test
    void searchWithErrors() throws Exception {
        var invalidCheckIn = TestUtils.format(LocalDate.now().minusDays(20));
        String invalidRequest = TestUtils.searchRequest(hotelId, invalidCheckIn, checkOut, Arrays.asList("10", "20"));
        String invalidRequest2 = TestUtils.searchRequest(hotelId, "0023/13/13", "Smarch",
                Arrays.asList("10", "20"));
        String invalidRequest3 = TestUtils.searchRequest(hotelId, checkIn, checkOut, Arrays.asList("-10", "140"));
        String invalidRequest4 = TestUtils.searchRequest("", checkIn, checkOut, Arrays.asList("10", "20"));

        mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("The checkIn or checkOut is not valid"));

        mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(invalidRequest3))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Ages not valid"));

        mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(invalidRequest2))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"errors\":[\"Invalid date format\"]}"));

        mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(invalidRequest4))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("The hotelId is not valid"));
    }

    @Test
    void count() throws Exception {
        var request = TestUtils.searchRequest(hotelId, checkIn, checkOut,
                Arrays.asList("10", "20"));

        var searchResult = mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").isString()).andReturn();

        mvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(request));

        var objetResult = this.objectMapper.readValue(searchResult.getResponse().getContentAsString(),
                SearchResponseDTO.class);

        var expectedResult = new CountResponseDTO(objetResult.searchId(),
                new ReservationDTO(hotelId,
                        LocalDate.now(),
                        LocalDate.now().plusDays(4),
                        Arrays.asList(10, 20)), 2);

        var countResult = mvc.perform(get("/count").param("searchId", objetResult.searchId()))
                .andExpect(status().isOk()).andReturn();

        assertThat(countResult.getResponse().getContentAsString())
                .isEqualTo(this.objectMapper.writeValueAsString(expectedResult));
    }

    @Test
    void countWithErrors() throws Exception {
        mvc.perform(get("/count")).andExpect(status().isBadRequest());

        mvc.perform(get("/count").param("searchId", String.valueOf(-10)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]").value("Search not found with id: -10"));
    }

}