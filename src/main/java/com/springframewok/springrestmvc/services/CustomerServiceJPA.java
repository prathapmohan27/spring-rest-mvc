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
import java.util.concurrent.atomic.AtomicReference;
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
        return customerMapper.toCustomerDTO(customerRepository.save(customerMapper.toCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(existingCustomer -> {
            existingCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper.toCustomerDTO(customerRepository.save(existingCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) return false;
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(existingCustomer -> {
            if(!existingCustomer.getCustomerName().equals(customer.getCustomerName())) {
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper.toCustomerDTO(customerRepository.save(existingCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();

    }
}
