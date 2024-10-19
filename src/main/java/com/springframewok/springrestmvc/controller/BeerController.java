package com.springframewok.springrestmvc.controller;


import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController  {
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer findBeerById(@PathVariable("beerId") UUID beerId) {
        log.info("Find beer by id: {}", beerId);
        return beerService.findBeerById(beerId);
    }
}
