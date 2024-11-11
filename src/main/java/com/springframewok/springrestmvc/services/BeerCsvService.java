package com.springframewok.springrestmvc.services;

import com.springframewok.springrestmvc.model.BeerCsvRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCsvRecord> convertCsvToBeers(File csvFile);
}
