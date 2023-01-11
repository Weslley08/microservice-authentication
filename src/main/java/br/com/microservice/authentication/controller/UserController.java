package br.com.microservice.authentication.controller;

import br.com.microservice.authentication.model.ResponseEntityCustom;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.enums.FindType;
import br.com.microservice.authentication.model.enums.TypeUpdateEnum;
import br.com.microservice.authentication.model.request.UpdateRequest;
import br.com.microservice.authentication.service.UserService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.microservice.authentication.model.constants.RoutesConstants.*;

@RestController
@Timed(histogram = true, value = "user")
@RequestMapping(value = BASE_PATH_AND_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = USERS_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
        return ResponseEntityCustom.created(userService.createUser(userDto));
    }

    @PatchMapping(value = USERS_PATH + "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(@RequestBody @Valid UpdateRequest updateRequest,
                                          @RequestParam(value = "type_update") TypeUpdateEnum typeUpdate) {
        return ResponseEntityCustom.ok(userService.update(updateRequest, typeUpdate));
    }

    @GetMapping(value = USERS_PATH + "/find")
    public ResponseEntity<UserDto> findById(@RequestParam("find_type") FindType findType) {
        return ResponseEntityCustom.ok(userService.find(findType));
    }
}
