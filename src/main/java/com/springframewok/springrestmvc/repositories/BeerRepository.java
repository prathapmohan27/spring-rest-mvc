package com.springframewok.springrestmvc.repositories;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository <Beer, UUID> {

    Page<Beer> findAllByBeerNameLikeIgnoreCase(String beerName,  Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
}
