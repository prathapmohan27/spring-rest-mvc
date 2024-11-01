package com.springframewok.springrestmvc.mappers;

import com.springframewok.springrestmvc.entities.Customer;
import com.springframewok.springrestmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer toCustomer(CustomerDTO customerDTO);
    CustomerDTO toCustomerDTO(Customer customer);
}
