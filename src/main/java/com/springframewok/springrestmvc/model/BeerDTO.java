package com.springframewok.springrestmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private String brand;
    private Integer quantityOnHand;
    private BigDecimal  price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
