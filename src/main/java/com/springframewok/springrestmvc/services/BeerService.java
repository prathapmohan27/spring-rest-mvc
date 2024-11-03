package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> getAllBeers();
    Optional<BeerDTO> findBeerById(UUID id);
    BeerDTO saveBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer);
    Boolean deleteBeer(UUID id);
    Optional<BeerDTO> patchBeer(UUID id, BeerDTO beer);
}
