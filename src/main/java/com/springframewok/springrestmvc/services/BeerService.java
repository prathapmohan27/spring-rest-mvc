package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> getAllBeers();
    Optional<BeerDTO> findBeerById(UUID id);
    BeerDTO saveBeer(BeerDTO beer);
    void updateBeer(UUID id, BeerDTO beer);
    void deleteBeer(UUID id);
    void patchBeer(UUID id, BeerDTO beer);
}
