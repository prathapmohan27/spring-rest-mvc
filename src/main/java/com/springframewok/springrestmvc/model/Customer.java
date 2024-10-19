package com.springframewok.springrestmvc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class Customer {
    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
