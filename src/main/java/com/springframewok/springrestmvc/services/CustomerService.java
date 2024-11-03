package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> findAll();
    Optional<CustomerDTO> findById(UUID id);
    CustomerDTO saveCustomer(CustomerDTO customer);
    Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer);
    Boolean deleteCustomer(UUID id);
    Optional<CustomerDTO> patchCustomer(UUID id, CustomerDTO customer);
}
