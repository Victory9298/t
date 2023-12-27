package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.controller.DataController;
import com.example.model.Data;
import com.example.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DataController.class)
public class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;
    private Data testData = new Data();;

    @BeforeEach
    void setUp() throws ParseException {
        testData.setId(1);
        testData.setCountry("TestCountry");
        testData.setActive(100);
        testData.setDeath(50);
        testData.setRecovered(30);
    }

    @Test
    public void testGetDataById() throws Exception {

        ResponseEntity<Data> responseEntity = ResponseEntity.ok(testData);
        Mockito.when(dataService.getDataById(1)).thenReturn(responseEntity);

        mockMvc.perform(get("/covid/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("TestCountry"))
                .andExpect(jsonPath("$.active").value(100))
                .andExpect(jsonPath("$.death").value(50))
                .andExpect(jsonPath("$.recovered").value(30));
    }

    @Test
    public void testGetDataById_ReturnsNotFound() throws Exception {
        Mockito.when(dataService.getDataById(1)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/covid/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTop5Data_ReturnsOk() throws Exception {
        List<Data> testDataList = Arrays.asList(
                new Data(1, "Country1", 100, 50, 30),
                new Data(2, "Country2", 90, 40, 25)
        );

        ResponseEntity<List<Data>> responseEntity = ResponseEntity.ok(testDataList);
        Mockito.when(dataService.getTop5Data("death")).thenReturn(responseEntity);

        mockMvc.perform(get("/covid/top5").param("by", "death"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Country1"))
                .andExpect(jsonPath("$[1].country").value("Country2"));
    }

    @Test
    public void testGetTop5Data_ReturnsBadRequest() throws Exception {
        Mockito.when(dataService.getTop5Data("invalid")).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(get("/covid/top5").param("by", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTotalData_ReturnsOk() throws Exception {
        Integer testTotal = 500; // Replace with your test data

        ResponseEntity<Integer> responseEntity = ResponseEntity.ok(testTotal);
        Mockito.when(dataService.getTotalData("death")).thenReturn(responseEntity);

        mockMvc.perform(get("/covid/total").param("by", "death"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(500));
    }

    @Test
    public void testGetTotalData_ReturnsBadRequest() throws Exception {
        Mockito.when(dataService.getTotalData("invalid")).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(get("/covid/total").param("by", "invalid"))
                .andExpect(status().isBadRequest());
    }

}
