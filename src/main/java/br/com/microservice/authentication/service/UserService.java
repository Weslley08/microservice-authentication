package br.com.microservice.authentication.service;

import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.enums.FindType;
import br.com.microservice.authentication.model.enums.TypeUpdateEnum;
import br.com.microservice.authentication.model.request.UpdateRequest;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto find(FindType findType);

    UserDto update(UpdateRequest updateRequest, TypeUpdateEnum typeUpdateEnum);
}
