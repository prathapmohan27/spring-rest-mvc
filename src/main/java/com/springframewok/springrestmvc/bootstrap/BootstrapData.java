package com.springframewok.springrestmvc.bootstrap;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.entities.Customer;
import com.springframewok.springrestmvc.model.BeerDTO;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.model.CustomerDTO;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeers();
        loadCustomers();
    }

    private void loadBeers() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("kingfisher")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("11213")
                    .price(new BigDecimal("50.99"))
                    .quantityOnHand(123)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Galaxy cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("5513")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(53)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("black")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("1123")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(13)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();
            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }

    }

    private void loadCustomers() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("John Doe")
                    .creationDate(LocalDateTime.now())
                    .lastUpdateDate(LocalDateTime.now())
                    .build();
            Customer customer2 = Customer.builder()
                    .customerName("luffy")
                    .creationDate(LocalDateTime.now())
                    .lastUpdateDate(LocalDateTime.now())
                    .build();
            Customer customer3 = Customer.builder()
                    .customerName("zoro")
                    .creationDate(LocalDateTime.now())
                    .lastUpdateDate(LocalDateTime.now())
                    .build();
            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
