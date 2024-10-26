package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAll();
    Optional<Customer> findById(UUID id);
    Customer saveCustomer(Customer customer);
    void updateCustomer(UUID id, Customer customer);
    void deleteCustomer(UUID id);
    void patchCustomer(UUID id, Customer customer);
}
