package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.model.Customer;
import com.springframewok.springrestmvc.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping( value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.info("Get customer by id: {}", customerId);
        return customerService.findById(customerId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        log.info("Get all customers");
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        log.info("Create customer: {}", customer);
        Customer data = customerService.saveCustomer(customer);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(HttpHeaders.LOCATION, "/api/v1/customer/" + data.getId());
        return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
    }

    @PutMapping(value = "{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
        log.info("Update customer: {}", customer);
        customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") UUID customerId) {
        log.info("Delete customer: {}", customerId);
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "{customerId}")
    public ResponseEntity<?> patchCustomer(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        log.info("Patch customer: {}", customer);
        customerService.patchCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
