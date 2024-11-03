package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.mappers.BeerMapper;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    BeerMapper beerMapper;

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

    @Rollback
    @Transactional
    @Test
    void createBeer() {
     BeerDTO beerDTO = BeerDTO.builder().beerName("king fisher").build();
     ResponseEntity<?> responseEntity = beerController.createBeer(beerDTO);
     assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
     assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

     UUID id = UUID.fromString(responseEntity.getHeaders().getLocation().getPath().split("/")[4]);
     Beer beer = beerRepository.findById(id).get();
     assertThat(beer).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void updateBeer() {
        BeerDTO beer = beerMapper.toBeerDTO(beerRepository.findAll().getFirst());
        final String name = "old";
        beer.setBeerName(name);

        ResponseEntity<?> responseEntity = beerController.updateBeer(beer.getId(), beer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(name);

    }

    @Test
    void updateBeerNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.updateBeer(UUID.randomUUID(), null));
    }

    @Rollback
    @Transactional
    @Test
    void deleteBeer() {
        BeerDTO beer = beerMapper.toBeerDTO(beerRepository.findAll().getFirst());
        ResponseEntity<?> responseEntity = beerController.deleteBeer(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty()).isTrue();
    }

    @Test
    void deleteBeerNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.deleteBeer(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void patchBeer() {
        BeerDTO beer = beerMapper.toBeerDTO(beerRepository.findAll().getFirst());
        beer.setBeerName("updated");
        beer.setPrice(BigDecimal.valueOf(100.00));
        ResponseEntity<?> responseEntity = beerController.patchBeer(beer.getId(), beer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

    }

}