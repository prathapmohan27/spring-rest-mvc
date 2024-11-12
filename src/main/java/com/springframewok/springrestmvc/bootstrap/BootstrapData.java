package com.springframewok.springrestmvc.bootstrap;

import com.springframewok.springrestmvc.entities.Beer;
import com.springframewok.springrestmvc.entities.Customer;
import com.springframewok.springrestmvc.model.BeerCsvRecord;
import com.springframewok.springrestmvc.model.BeerStyle;
import com.springframewok.springrestmvc.repositories.BeerRepository;
import com.springframewok.springrestmvc.repositories.CustomerRepository;
import com.springframewok.springrestmvc.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeers();
        loadCsv();
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

    private void loadCsv() throws FileNotFoundException {
        if (beerRepository.count() < 10){
            File file = ResourceUtils.getFile("classpath:csvData/beers.csv");

            List<BeerCsvRecord> recs = beerCsvService.convertCsvToBeers(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }
    }
}
