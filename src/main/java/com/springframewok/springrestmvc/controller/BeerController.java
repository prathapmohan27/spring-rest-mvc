package com.springframewok.springrestmvc.controller;


import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public List<BeerDTO> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping(BEER_BY_ID_URI)
    public BeerDTO findBeerById(@PathVariable("beerId") UUID beerId) {
        log.info("Find beer by id: {}", beerId);
        return beerService.findBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_URI)
    public ResponseEntity<?> createBeer( @Validated @RequestBody BeerDTO beer) {
        log.info("Create beer: {}", beer);
        BeerDTO data = beerService.saveBeer(beer);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/api/v1/beer/" + data.getId());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> updateBeer( @PathVariable("beerId") UUID id, @Validated @RequestBody BeerDTO beer) {
        log.info("Update beer: {}", beer);
        if(beerService.updateBeer(id, beer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> deleteBeer(@PathVariable("beerId") UUID beerId) {
        log.info("Delete beer: {}", beerId);
        if(!beerService.deleteBeer(beerId)) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_BY_ID_URI)
    public ResponseEntity<?> patchBeer(@PathVariable("beerId") UUID beerId,  @RequestBody BeerDTO beer) {
        log.info("Patch beer: {}", beer);
        if(beerService.patchBeer(beerId, beer).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
