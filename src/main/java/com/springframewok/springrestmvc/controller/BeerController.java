package com.springframewok.springrestmvc.controller;


import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController  {
    private final BeerService beerService;

    public Beer findBeerById(UUID id) {
        log.info("Find beer by id: {}", id);
        return beerService.findBeerById(id);
    }
}
