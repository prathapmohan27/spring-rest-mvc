package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("kingfisher")
                .beerStyle(BeerStyle.LAGER)
                .upc("11213")
                .price(new BigDecimal("50.99"))
                .quantityOnHand(123)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("5513")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(53)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("black")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1123")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(13)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);

    }

    @Override
    public List<Beer> getAllBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer findBeerById(UUID id) {
        log.info("Find beer by id: {}", id);
        return beerMap.get(id);
    }
}
