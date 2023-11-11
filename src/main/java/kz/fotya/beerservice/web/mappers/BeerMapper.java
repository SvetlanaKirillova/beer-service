package kz.fotya.beerservice.web.mappers;

import kz.fotya.beerservice.domain.Beer;
import kz.fotya.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    Beer beerFromBeerDto(BeerDto beerDto);
    BeerDto beerDtoFromBeer(Beer beer);
}
