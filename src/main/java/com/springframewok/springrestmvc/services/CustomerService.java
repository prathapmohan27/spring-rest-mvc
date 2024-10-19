package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAll();
    Customer findById(UUID id);
}
