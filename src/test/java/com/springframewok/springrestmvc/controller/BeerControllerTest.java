package com.springframewok.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> beerIdArgumentCaptor;

    @MockitoBean
    BeerServiceImpl beerServiceImpl;

    Map<UUID, BeerDTO> beerMap = new HashMap<>();
    ArrayList<BeerDTO> beers;

    @BeforeEach
    void setUp() {
        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("crank")
                .beerStyle(BeerStyle.LAGER)
                .upc("11213")
                .price(new BigDecimal("50.99"))
                .quantityOnHand(123)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
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

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beers = new ArrayList<>(beerMap.values());
    }

    @Test
    void createBeer() throws Exception {
        BeerDTO beer = beers.getFirst();
        beer.setId(null);
        beer.setVersion(null);
        when(beerServiceImpl.saveBeer(any(BeerDTO.class))).thenReturn(beers.getLast());
        mockMvc.perform(post(BeerController.BEER_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateBeer() throws Exception {
        BeerDTO beer = beers.getFirst();
        beer.setVersion(10);
        when(beerServiceImpl.updateBeer(any(UUID.class), any(BeerDTO.class))).thenReturn(Optional.of(beer));
        mockMvc.perform(put(BeerController.BEER_BY_ID_URI, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());
        verify(beerServiceImpl).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void deleteBeer() throws Exception {
        BeerDTO beer = beers.getFirst();
        when(beerServiceImpl.deleteBeer(any(UUID.class))).thenReturn(true);
        mockMvc.perform(delete(BeerController.BEER_BY_ID_URI, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(beerServiceImpl).deleteBeer(beerIdArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(beerIdArgumentCaptor.getValue());
    }

    @Test
    void patchBeer() throws Exception {
        BeerDTO beer = beers.getFirst();
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "hello");
        when(beerServiceImpl.patchBeer(any(UUID.class), any(BeerDTO.class))).thenReturn(Optional.of(beer));
        mockMvc.perform(patch(BeerController.BEER_BY_ID_URI, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
        verify(beerServiceImpl).patchBeer(beerIdArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void listBeers() throws Exception {
        when(beerServiceImpl.getAllBeers()).thenReturn(beers);
        mockMvc.perform(get(BeerController.BEER_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void findBeerById() throws Exception {
        BeerDTO beer = beers.getFirst();
        when(beerServiceImpl.findBeerById(beer.getId())).thenReturn(Optional.of(beer));
        mockMvc.perform(get(BeerController.BEER_BY_ID_URI, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));
    }

    @Test
    void findBeerByIdNotFound() throws Exception {
        when(beerServiceImpl.findBeerById(any(UUID.class))).thenReturn(Optional.empty());
        mockMvc.perform(get(BeerController.BEER_BY_ID_URI, UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    void testNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        MvcResult result = mockMvc.perform(post(BeerController.BEER_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void testBeerPrice() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().price(BigDecimal.valueOf(0)).build();
        MvcResult result = mockMvc.perform(post(BeerController.BEER_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBlankName() throws Exception {
        BeerDTO beerDTO = beers.getFirst();
        beerDTO.setBeerName("");
        mockMvc.perform(put(BeerController.BEER_BY_ID_URI, beerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}
