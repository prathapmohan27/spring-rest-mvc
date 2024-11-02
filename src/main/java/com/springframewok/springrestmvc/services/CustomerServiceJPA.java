package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.mappers.CustomerMapper;
import com.springframewok.springrestmvc.model.CustomerDTO;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        return Optional.ofNullable(customerMapper.toCustomerDTO(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomer(UUID id) {

    }

    @Override
    public void patchCustomer(UUID id, CustomerDTO customer) {

    }
}
