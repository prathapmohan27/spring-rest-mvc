package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> getAllBeers();
    Beer findBeerById(UUID id);
    Beer saveBeer(Beer beer);
    void updateBeer(UUID id, Beer beer);
    void deleteBeer(UUID id);
    void patchBeer(UUID id, Beer beer);
}
