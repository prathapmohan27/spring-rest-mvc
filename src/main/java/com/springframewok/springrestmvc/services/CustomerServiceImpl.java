package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer data = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        customers.put(data.getId(), data);
        return data;
    }

    @Override
    public void updateCustomer(UUID id, Customer customer) {
        Customer exisitingCustomer = customers.get(id);
        exisitingCustomer.setCustomerName(customer.getCustomerName());
        exisitingCustomer.setVersion(customer.getVersion());
        exisitingCustomer.setLastUpdateDate(LocalDateTime.now());
        customers.put(id, exisitingCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        customers.remove(id);
    }

    @Override
    public void patchCustomer(UUID id, Customer customer) {
        Customer exisitingCustomer = customers.get(id);

        if (StringUtils.hasText(customer.getCustomerName())) {
            exisitingCustomer.setCustomerName(customer.getCustomerName());
        }
        if(exisitingCustomer.getVersion() != null) {
            exisitingCustomer.setVersion(customer.getVersion());
        }
    }
}
