package com.ahmadaghazadeh.users.ui.controllers;

import com.ahmadaghazadeh.users.data.UserEntity;
import com.ahmadaghazadeh.users.services.UserService;
import com.ahmadaghazadeh.users.shared.UserDto;
import com.ahmadaghazadeh.users.ui.models.CreateUserRequestModel;
import com.ahmadaghazadeh.users.ui.models.CreateUserResponseModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment environment;

    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public  ResponseEntity<String> check() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Welcome, You are online and application port is "+environment.getProperty("local.server.port"));
    }

    @GetMapping("/status/{name}")
    public  ResponseEntity<String> getOnline(@PathVariable("name")String name ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Welcome, You are online and application port is "+environment.getProperty("local.server.port")+". This is the variable passed "+name+"}");
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE,})
    public  ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userDetails, UserDto.class);

        UserDto createdUser= userService.CreateUser(userDto);

        CreateUserResponseModel returnValue= mapper.map(createdUser,CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(returnValue);
    }
}
