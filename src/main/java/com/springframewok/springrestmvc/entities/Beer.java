package com.springframewok.springrestmvc.entities;

import com.springframewok.springrestmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

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
    @JdbcTypeCode(SqlTypes.CHAR)
//   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 50)
    @Column(length = 50)
    private String beerName;
    @NotNull
    @JdbcTypeCode(value = SqlTypes.TINYINT)
    private BeerStyle beerStyle;
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String upc;
    private String brand;
    private Integer quantityOnHand;
    @Positive
    @NotNull
    private BigDecimal price;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedOn;
}
