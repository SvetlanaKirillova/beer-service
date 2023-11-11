package kz.fotya.beerservice.repositories;

import kz.fotya.beerservice.domain.Beer;
import kz.fotya.beerservice.web.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);
    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
    Page<Beer> findAllByBeerNameAndAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);
    Beer findByUpc(String upc);
}
