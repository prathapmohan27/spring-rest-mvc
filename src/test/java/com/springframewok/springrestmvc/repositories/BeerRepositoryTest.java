package com.springframewok.springrestmvc.repositories;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void saveBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("new beer")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456")
                .price(new BigDecimal("100"))
                .build());
        beerRepository.flush();
        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
        assertThat(beer.getBeerName()).isEqualTo("new beer");


    }
}