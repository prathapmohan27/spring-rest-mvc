package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.model.CustomerDTO;
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
public class CustomerController {
    public static final String CUSTOMER_URI = "/api/v1/customer";
    public static final String CUSTOMER_BY_ID_URI = CUSTOMER_URI + "/{customerId}";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(CUSTOMER_BY_ID_URI)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.info("Get customer by id: {}", customerId);
        return customerService.findById(customerId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(CUSTOMER_URI)
    public List<CustomerDTO> getAllCustomers() {
        log.info("Get all customers");
        return customerService.findAll();
    }

    @PostMapping(CUSTOMER_URI)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customer) {
        log.info("Create customer: {}", customer);
        CustomerDTO data = customerService.saveCustomer(customer);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(HttpHeaders.LOCATION, "/api/v1/customer/" + data.getId());
        return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {
        log.info("Update customer: {}", customer);
        if (customerService.updateCustomer(id, customer).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") UUID customerId) {
        log.info("Delete customer: {}", customerId);
        if (!customerService.deleteCustomer(customerId)) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_BY_ID_URI)
    public ResponseEntity<?> patchCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        log.info("Patch customer: {}", customer);
        if(customerService.patchCustomer(customerId, customer).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
