package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer findBeerById(UUID id);
}
