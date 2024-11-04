package com.springframewok.springrestmvc.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BeerDTO {
    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotBlank
    @NotNull
    private String upc;
    private String brand;
    private Integer quantityOnHand;
    @Positive
    @NotNull
    private BigDecimal  price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
