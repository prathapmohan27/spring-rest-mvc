package com.springframewok.springrestmvc.repositories;

import com.springframewok.springrestmvc.bootstrap.BootstrapData;
import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void getBeerByName() {
        Page<Beer> beerList = beerRepository.findAllByBeerNameLikeIgnoreCase("%IPA%", null);
        assertThat(beerList).isNotEmpty();
    }

    @Test
    void getBeerByStyle() {
        Page<Beer> beerList = beerRepository.findAllByBeerStyle(BeerStyle.valueOf("PILSNER"), null);
        assertThat(beerList).isNotEmpty();
    }

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