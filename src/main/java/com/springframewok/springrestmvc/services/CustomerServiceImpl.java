package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Beer;
import com.springframewok.springrestmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map <UUID, Customer> customers = new HashMap<>();

    public CustomerServiceImpl() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Doe")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("luffy")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("zoro")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);

    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer findById(UUID id) {
        return customers.get(id);
    }
}
