package br.com.microservice.authentication.mapper;

import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.request.UpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper REFERENCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);

    UserDto update(UpdateRequest updateRequest);

}
