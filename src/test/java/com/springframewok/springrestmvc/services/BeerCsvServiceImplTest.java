package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerCsvRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertBeerToCsv() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvData/beers.csv");
        List<BeerCsvRecord> beers = beerCsvService.convertCsvToBeers(file);

        System.out.println(beers.size());

        assertThat(beers.size()).isGreaterThan(0);

    }

}