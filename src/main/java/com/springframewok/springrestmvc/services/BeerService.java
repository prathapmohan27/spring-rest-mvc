package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> getAllBeers();
    Beer findBeerById(UUID id);
}
