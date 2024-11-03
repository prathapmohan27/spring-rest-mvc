package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.mappers.BeerMapper;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getAllBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> findBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.toBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        return beerMapper.toBeerDTO(beerRepository.save(beerMapper.toBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> beerDTO = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            beerDTO.set(Optional.of(beerMapper.toBeerDTO(beerRepository.save(foundBeer))));
        }, () -> {
            beerDTO.set(Optional.empty());
        });
        return beerDTO.get();
    }

    @Override
    public Boolean deleteBeer(UUID id) {
        if (!beerRepository.existsById(id)) return false;
        beerRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeer(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> beerDTO = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(existingBeer -> {
            if (StringUtils.hasText(beer.getUpc())) {
                existingBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null) {
                existingBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null) {
                existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if (StringUtils.hasText(beer.getBeerName())) {
                existingBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                existingBeer.setBeerStyle(beer.getBeerStyle());
            }
            beerDTO.set(Optional.of(beerMapper.toBeerDTO(beerRepository.save(existingBeer))));
        }, () -> {
            beerDTO.set(Optional.empty());
        });
        return beerDTO.get();
    }
}
