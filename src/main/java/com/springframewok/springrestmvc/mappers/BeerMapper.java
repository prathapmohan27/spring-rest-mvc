package com.springframewok.springrestmvc.mappers;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer toBeer(BeerDTO beer);
    BeerDTO toBeerDTO(Beer beer);
}
