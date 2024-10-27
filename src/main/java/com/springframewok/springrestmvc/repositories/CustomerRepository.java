package com.springframewok.springrestmvc.repositories;

import com.springframewok.springrestmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
