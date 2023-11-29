package kz.fotya.beerservice.services.brewing;

import kz.fotya.beerservice.domain.Beer;
import kz.fotya.beerservice.repositories.BeerRepository;
import kz.fotya.beerservice.services.inventory.BeerInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService inventoryService;

    @Scheduled(fixedRate = 5000)
    public void checkFoLowInventory(){

        List<Beer> beerList = beerRepository.findAll();
        beerList.forEach( beer -> {
            int invOnHand = inventoryService.getOnHandInventory(beer.getId());

            log.debug("Checking inventory for: " + beer.getBeerName() + "/" + beer.getId());
            log.debug("Min On hand = " + beer.getMinOnHand());
            log.debug("Inventory on hand = " + invOnHand);

            if (invOnHand < beer.getMinOnHand()) {
                //todo send even to brew more beer
            }
        });
    }
}
