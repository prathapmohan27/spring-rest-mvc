package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.entities.Customer;
import com.springframewok.springrestmvc.model.CustomerDTO;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void checkIsEmpty() {
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(0);

    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
    }

    @Test
    void getCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

}