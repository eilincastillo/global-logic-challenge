package global.logic.challenge.mapper;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
     public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    public abstract User dtoToUser(SignUpRequestDTO product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "isActive", target = "isActive")
    public abstract UserLoginResponseDTO userToUserSignUpResponse(User product);
}
