package kz.fotya.beerservice.services;

import kz.fotya.beerservice.domain.Beer;
import kz.fotya.beerservice.repositories.BeerRepository;
import kz.fotya.beerservice.services.inventory.BeerInventoryService;
import kz.fotya.beerservice.web.mappers.BeerMapper;
import kz.fotya.beerservice.web.model.BeerDto;
import kz.fotya.beerservice.web.model.BeerPagedList;
import kz.fotya.beerservice.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private BeerInventoryService beerInventoryService;


//    public BeerServiceImpl(BeerInventoryService beerInventoryService) {
//        this.beerInventoryService = beerInventoryService;
//    }

    @Override
    public BeerDto getBeerById(UUID beerId) {

        return beerMapper.beerDtoFromBeer(beerRepository.findById(beerId).orElseThrow());
    }

    @Override
    public BeerPagedList getBeerList(String beerName,
                                     BeerStyle beerStyle,
                                     PageRequest pageRequest,
                                     Boolean showInventoryOnHand) {

        System.out.println("ALL BEER: " + beerRepository.findAll());
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (StringUtils.hasLength(beerName) && beerStyle!=null){
            beerPage = beerRepository.findAllByBeerNameAndAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasLength(beerName)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (beerStyle!=null) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

//        .forEach(beerDto ->  beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beerDto.getId())))
        List<BeerDto> listOfBeersDto = beerPage.getContent()
                .stream()
                .map(beerMapper::beerDtoFromBeer)
                .collect(Collectors.toList());
        listOfBeersDto.forEach(beerDto ->  beerDto.setQuantityOnHand(
                beerInventoryService.getOnHandInventory(beerDto.getId())
                ));
        beerPagedList = new BeerPagedList(listOfBeersDto,
                pageRequest,
                beerPage.getTotalElements());


        return beerPagedList;
    }

    @Override
    public BeerDto getBeerByUpc(String upc) {
        return beerMapper.beerDtoFromBeer(beerRepository.findByUpc(upc));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerDtoFromBeer(beerRepository.save(beerMapper.beerFromBeerDto(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        return beerMapper.beerDtoFromBeer(beerRepository.save(beerMapper.beerFromBeerDto(beerDto)));
    }
}
