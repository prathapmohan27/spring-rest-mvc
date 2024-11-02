package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void listBeers() {
        List<BeerDTO> beers = beerController.getAllBeers();
        assertThat(beers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void checkEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beers = beerController.getAllBeers();
        assertThat(beers.size()).isEqualTo(0);
    }


    @Test
    void getBeerById() {
        BeerDTO beer = beerController.getAllBeers().getFirst();
        assertThat(beer).isNotNull();
    }

    @Test
    void getBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.findBeerById(UUID.randomUUID()));
    }

}