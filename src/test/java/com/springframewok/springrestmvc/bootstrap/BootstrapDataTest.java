package com.springframewok.springrestmvc.bootstrap;

import com.springframewok.springrestmvc.repositories.BeerRepository;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    private BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository);
    }

    @Test
    void testBootstrapData() throws Exception {
        bootstrapData.run(null);
        long beerCount = beerRepository.count();
        long customerCount = customerRepository.count();
        assertThat(beerCount).isEqualTo(3);
        assertThat(customerCount).isEqualTo(3);
    }
}