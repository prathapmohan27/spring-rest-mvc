package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.mappers.BeerMapper;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private static final Integer DEFAULT_PAGE_NUMBER = 1;

    @Override
    public Page<BeerDTO> getAllBeers(String beerName, BeerStyle beerStyle, Integer pageNumber, Integer pageSize) {

        PageRequest pageData = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerPage;
        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeerByName(beerName, (Pageable) pageData);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeerByStyle(beerStyle, (Pageable) pageData);
        } else {
            beerPage = beerRepository.findAll(pageData);
        }
        return beerPage.map(beerMapper::toBeerDTO);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;
        int queryPageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Sort sort = Sort.by(Sort.Direction.ASC, "beerName");
        return PageRequest.of(queryPageNumber - 1, queryPageSize, sort);
    }

    public Page<Beer> listBeerByName(String name, Pageable pageable) {
        return beerRepository.findAllByBeerNameLikeIgnoreCase("%" + name + "%", pageable);
    }

    public Page<Beer> listBeerByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
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
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
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
            atomicReference.set(Optional.of(beerMapper.toBeerDTO(beerRepository.save(existingBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
