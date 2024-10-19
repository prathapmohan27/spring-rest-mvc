package com.springframewok.springrestmvc.controller;

import com.springframewok.springrestmvc.model.Customer;
import com.springframewok.springrestmvc.services.CustomerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping( value = "{customerId}", method = RequestMethod.GET)
    Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.findById(customerId);
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Customer> getAllCustomers() {
        return customerService.findAll();
    }
}
