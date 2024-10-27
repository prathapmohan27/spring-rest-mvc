package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        BeerDTO beer1 = BeerDTO.builder()
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

        BeerDTO beer2 = BeerDTO.builder()
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

        BeerDTO beer3 = BeerDTO.builder()
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
    public List<BeerDTO> getAllBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> findBeerById(UUID id) {
        log.info("Find beer by id: {}", id);
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        BeerDTO data = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
        beerMap.put(data.getId(), data);
        return data;
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(id);
        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setBeerStyle(beer.getBeerStyle());
        existingBeer.setUpc(beer.getUpc());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        existingBeer.setUpdatedOn(LocalDateTime.now());
        beerMap.put(id, existingBeer);
    }

    @Override
    public void deleteBeer(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchBeer(UUID id, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(id);

        if(StringUtils.hasText(existingBeer.getUpc())) {
            existingBeer.setUpc(beer.getUpc());
        }
        if(existingBeer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
        }
        if(existingBeer.getQuantityOnHand() != null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }
        if(StringUtils.hasText(existingBeer.getBeerName())) {
            existingBeer.setBeerName(beer.getBeerName());
        }
        if(existingBeer.getBeerStyle() != null) {
            existingBeer.setBeerStyle(beer.getBeerStyle());
        }
    }
}
