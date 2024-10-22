package com.springframewok.springrestmvc.controller;

import com.jayway.jsonpath.JsonPath;
import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    BeerServiceImpl beerServiceImpl;

    @Test
    void findBeerById() throws Exception {
       Beer beer = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("kingfisher")
                .beerStyle(BeerStyle.LAGER)
                .upc("11213")
                .price(new BigDecimal("50.99"))
                .quantityOnHand(123)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
        when(beerServiceImpl.findBeerById(beer.getId())).thenReturn(beer);
        mockMvc.perform(get("/api/v1/beer/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));
    }
}
