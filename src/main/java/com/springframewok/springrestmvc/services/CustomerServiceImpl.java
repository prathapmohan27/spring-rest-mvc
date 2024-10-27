package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map <UUID, CustomerDTO> customers = new HashMap<>();

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("John Doe")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("luffy")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        CustomerDTO customer3 = CustomerDTO.builder()
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
    public List<CustomerDTO> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO data = CustomerDTO.builder()
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
    public void updateCustomer(UUID id, CustomerDTO customer) {
        CustomerDTO exisitingCustomer = customers.get(id);
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
    public void patchCustomer(UUID id, CustomerDTO customer) {
        CustomerDTO exisitingCustomer = customers.get(id);

        if (StringUtils.hasText(customer.getCustomerName())) {
            exisitingCustomer.setCustomerName(customer.getCustomerName());
        }
        if(exisitingCustomer.getVersion() != null) {
            exisitingCustomer.setVersion(customer.getVersion());
        }
    }
}
