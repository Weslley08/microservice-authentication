package br.com.microservice.authentication.controller;

import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.enums.TypeUpdate;
import br.com.microservice.authentication.model.request.UpdateRequest;
import br.com.microservice.authentication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice-authentication/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<ResponseData> update(@PathVariable("user_id") String userId,
                                               @RequestParam(required = false, name = "id_operador") String idOperador,
                                               @RequestBody @Valid UpdateRequest updateRequest,
                                               @RequestParam("type_update") TypeUpdate typeUpdate) {
        return userService.update(userId, idOperador, updateRequest, typeUpdate);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseData> findById(@PathVariable("user_id") String userId) {
        return userService.findById(userId);
    }
}
