package com.springframewok.springrestmvc.controller;


import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController  {
    public static final String BEER_URI = "/api/v1/beer";
    public static final String BEER_BY_ID_URI = BEER_URI  + "/{beerId}";
    private final BeerService beerService;

    @GetMapping(BEER_URI)
    public List<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping(BEER_BY_ID_URI)
    public Beer findBeerById(@PathVariable("beerId") UUID beerId) {
        log.info("Find beer by id: {}", beerId);
        return beerService.findBeerById(beerId);
    }

    @PostMapping(BEER_URI)
    public ResponseEntity<?> createBeer(@RequestBody Beer beer) {
        log.info("Create beer: {}", beer);
        Beer data = beerService.saveBeer(beer);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/api/v1/beer/" + data.getId());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> updateBeer( @PathVariable("beerId") UUID id,  @RequestBody Beer beer) {
        log.info("Update beer: {}", beer);
        beerService.updateBeer(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> deleteBeer(@PathVariable("beerId") UUID beerId) {
        log.info("Delete beer: {}", beerId);
        beerService.deleteBeer(beerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> patchBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        log.info("Patch beer: {}", beer);
        beerService.patchBeer(beerId, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
