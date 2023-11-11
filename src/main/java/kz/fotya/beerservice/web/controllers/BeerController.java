package kz.fotya.beerservice.web.controllers;

import kz.fotya.beerservice.services.BeerService;
import kz.fotya.beerservice.web.model.BeerDto;
import kz.fotya.beerservice.web.model.BeerPagedList;
import kz.fotya.beerservice.web.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @Autowired
    BeerService beerService;

    @GetMapping(produces = {"application/json"}, path = "beer")
    ResponseEntity<BeerPagedList> getBeerList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "beerName", required = false) String beerName,
                                              @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                              @RequestParam(value = "showInventoryOnHands", required = false) Boolean showInventoryOnHand){

        if (pageNumber == null || pageNumber < 0)
            pageNumber = DEFAULT_PAGE_NUMBER;

        if (pageSize == null || pageSize < 0)
            pageSize = DEFAULT_PAGE_SIZE;

        if (showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        BeerPagedList beerPagedList = beerService.getBeerList(beerName,
                                                            beerStyle,
                                                            PageRequest.of(pageNumber, pageSize),
                                                            showInventoryOnHand);
        return new ResponseEntity<>(beerPagedList, HttpStatus.OK);
    }

    @GetMapping("beer/{beerId}")
    ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @GetMapping("beerUpc/{upc}")
    ResponseEntity<BeerDto> getBeerByUpc(@PathVariable String upc){
        return new ResponseEntity<>(beerService.getBeerByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("/beer")
     ResponseEntity<BeerDto> saveNewBeer(@RequestBody BeerDto beerDto){
        log.info("New beer created with name=" + beerDto.getBeerName());
        return  new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    ResponseEntity<BeerDto> updateBeer(@PathVariable UUID beerId, @RequestBody BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

}
