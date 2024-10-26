package com.springframewok.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframewok.springrestmvc.model.Customer;
import com.springframewok.springrestmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Captor
    ArgumentCaptor<UUID> customerIdCaptor;

    @MockitoBean
    CustomerServiceImpl customerServiceImpl;

    Map<UUID, Customer> customerMap = new HashMap<>();
    ArrayList<Customer> customers;

    @BeforeEach
    void setUp() {
        customerMap = new HashMap<>();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Doe")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Antony")
                .version(12)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customers = new ArrayList<>(customerMap.values());
    }

    @Test
    void createCustomer() throws Exception {
        Customer customer = customers.getFirst();
        customer.setId(null);
        customer.setVersion(null);
        when(customerServiceImpl.saveCustomer(any(Customer.class))).thenReturn(customers.getLast());
        mockMvc.perform(post(CustomerController.CUSTOMER_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCustomer() throws Exception {
        Customer customer = customers.getFirst();
        customer.setVersion(10);
        mockMvc.perform(put(CustomerController.CUSTOMER_BY_ID_URI, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());
        verify(customerServiceImpl).updateCustomer(any(UUID.class), any(Customer.class));
    }

    @Test
    void deleteCustomer() throws Exception {
        Customer customer = customers.getFirst();
        mockMvc.perform(delete(CustomerController.CUSTOMER_BY_ID_URI, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerServiceImpl).deleteCustomer(customerIdCaptor.capture());
        assertThat(customer.getId()).isEqualTo(customerIdCaptor.getValue());
    }

    @Test
    void patchCustomer() throws Exception {
        Customer customer = customers.getFirst();
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "John wick");
        mockMvc.perform(patch(CustomerController.CUSTOMER_BY_ID_URI, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());
        verify(customerServiceImpl).patchCustomer(customerIdCaptor.capture(), customerCaptor.capture());
        assertThat(customerMap.get("customerName")).isEqualTo(customerCaptor.getValue().getCustomerName());
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = customers.getFirst();
        when(customerServiceImpl.findById(customer.getId())).thenReturn(Optional.of(customer));
        mockMvc.perform(get(CustomerController.CUSTOMER_BY_ID_URI, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerServiceImpl.findAll()).thenReturn(customers);
        mockMvc.perform(get(CustomerController.CUSTOMER_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        when(customerServiceImpl.findById(any(UUID.class))).thenReturn(Optional.empty());
        mockMvc.perform(get(CustomerController.CUSTOMER_BY_ID_URI, UUID.randomUUID())).andExpect(status().isNotFound());
    }

}