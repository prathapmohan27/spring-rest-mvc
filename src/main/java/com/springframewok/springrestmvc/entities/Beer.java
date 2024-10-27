package com.springframewok.springrestmvc.entities;

import com.springframewok.springrestmvc.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
//   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private String brand;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
