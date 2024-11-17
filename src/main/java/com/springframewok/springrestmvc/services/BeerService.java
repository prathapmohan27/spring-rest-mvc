package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Page<BeerDTO> getAllBeers(String beerName, BeerStyle beerStyle, Integer pageNumber, Integer pageSize);
    Optional<BeerDTO> findBeerById(UUID id);
    BeerDTO saveBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer);
    Boolean deleteBeer(UUID id);
    Optional<BeerDTO> patchBeer(UUID id, BeerDTO beer);
}
