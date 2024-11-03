package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.entities.Customer;
import com.springframewok.springrestmvc.mappers.CustomerMapper;
import com.springframewok.springrestmvc.model.CustomerDTO;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CustomerMapper customerMapper;


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

    @Rollback
    @Transactional
    @Test
    void updateCustomer() {
        CustomerDTO customer = customerMapper.toCustomerDTO(customerRepository.findAll().getFirst());
        final String name = "updated";
        customer.setCustomerName(name);
        ResponseEntity<?>  responseEntity = customerController.updateCustomer(customer.getId(), customer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName()).isEqualTo(name);

    }

    @Test
    void updateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomer(UUID.randomUUID(), null));
    }

    @Transactional
    @Rollback
    @Test
    void createCustomer() {
        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customerRepository.findAll().getFirst());
        customerDTO.setCustomerName("updated");
        customerDTO.setId(null);
        ResponseEntity<?>  responseEntity = customerController.createCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        UUID id = UUID.fromString(responseEntity.getHeaders().getLocation().getPath().split("/")[4]);
        assertThat(customerRepository.findById(id).get().getCustomerName()).isEqualTo(customerDTO.getCustomerName());
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomer() {
        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customerRepository.findAll().getFirst());
        ResponseEntity<?>  responseEntity = customerController.deleteCustomer(customerDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Rollback
    @Transactional
    @Test
    void patchCustomer() {
        CustomerDTO customer = customerMapper.toCustomerDTO(customerRepository.findAll().getFirst());
        final String name = "updated";
        customer.setCustomerName(name);
        ResponseEntity<?>  responseEntity = customerController.updateCustomer(customer.getId(), customer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName()).isEqualTo(name);
    }

    @Test
    void patchCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.patchCustomer(UUID.randomUUID(), null));
    }

}