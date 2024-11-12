package com.springframewok.springrestmvc.bootstrap;

import com.springframewok.springrestmvc.controller.BeerController;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import com.springframewok.springrestmvc.services.BeerCsvService;
import com.springframewok.springrestmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    private BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void testBootstrapData() throws Exception {
        bootstrapData.run(null);
        long beerCount = beerRepository.count();
        long customerCount = customerRepository.count();
        assertThat(beerCount).isEqualTo(2413);
        assertThat(customerCount).isEqualTo(3);
    }
}