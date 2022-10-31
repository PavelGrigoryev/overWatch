package by.grigoryev.overwatch.mapper;

import by.grigoryev.overwatch.dto.CoinDto;
import by.grigoryev.overwatch.model.Coin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoinMapper {

    CoinDto toCoinDto(Coin coin);

}
