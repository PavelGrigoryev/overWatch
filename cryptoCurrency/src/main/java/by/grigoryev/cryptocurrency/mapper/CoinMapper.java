package by.grigoryev.cryptocurrency.mapper;

import by.grigoryev.cryptocurrency.dto.CoinDto;
import by.grigoryev.cryptocurrency.model.Coin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoinMapper {

    CoinDto toCoinDto(Coin coin);

}
