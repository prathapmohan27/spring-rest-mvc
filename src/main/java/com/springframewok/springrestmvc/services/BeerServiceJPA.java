package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.mappers.BeerMapper;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        return null;
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeer(UUID id) {

    }

    @Override
    public void patchBeer(UUID id, BeerDTO beer) {

    }
}
