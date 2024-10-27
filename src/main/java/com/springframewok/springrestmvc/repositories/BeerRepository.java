package com.springframewok.springrestmvc.repositories;

import com.springframewok.springrestmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository <Beer, UUID> {
}
