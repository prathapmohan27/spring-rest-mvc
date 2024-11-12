package com.springframewok.springrestmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.springframewok.springrestmvc.model.BeerCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {

    @Override
    public List<BeerCsvRecord> convertCsvToBeers(File csvFile) {
        try {
            List<BeerCsvRecord> beers;
            beers = new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(csvFile))
                    .withType(BeerCsvRecord.class)
                    .build().parse();
            return beers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
