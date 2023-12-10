package global.logic.challenge.mapper;

import global.logic.challenge.dto.PhoneDTO;

import global.logic.challenge.model.Phone;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PhoneMapper {

    public static final PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    @Mapping(source = "number", target = "number")
    @Mapping(source = "citycode", target = "citycode")
    @Mapping(source = "countrycode", target = "countrycode")
    public abstract PhoneDTO phoneToPhoneDTO(Phone product);
}
