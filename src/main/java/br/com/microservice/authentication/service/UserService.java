package br.com.microservice.authentication.service;

import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.enums.TypeUpdate;
import br.com.microservice.authentication.model.request.UpdateRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseData> createUser(UserDto userDto);

    ResponseEntity<ResponseData> update(String userId,
                                        String idOperador,
                                        UpdateRequest updateRequest,
                                        TypeUpdate typeUpdate);

    ResponseEntity<ResponseData> findById(String userId);
}
