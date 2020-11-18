package com.iiht.userservice.controllers;

import com.iiht.userservice.dto.UserDto;
import com.iiht.userservice.entities.User;
import com.iiht.userservice.exceptions.AccountActivationException;
import com.iiht.userservice.exceptions.UserNotFound;
import com.iiht.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<Iterable<UserDto>> getAll() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> byUsername(@PathVariable String username) throws UserNotFound {
        Optional<UserDto> cr = userService.findByUsername(username);

        if (cr.isEmpty()) {
            throw new UserNotFound("Custormer with the username " + username + " is not found");
        }
        return new ResponseEntity<>(cr.get(), HttpStatus.FOUND);
    }

    @GetMapping("/search/name/{partName}")
    public ResponseEntity<Iterable<UserDto>> byName(@PathVariable String partName) {

        Iterable<UserDto> usersFound = userService.getByName(partName);
        return new ResponseEntity<>(usersFound, HttpStatus.OK);
    }

    @PostMapping("/new_user")
    public ResponseEntity<String> saveUser(@RequestBody User user) {

        userService.saveCustomer(user);
        return new ResponseEntity<>("Account created", HttpStatus.ACCEPTED);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestBody ActivationBody activationBody) throws AccountActivationException {
        this.userService.activate(activationBody.username, activationBody.email, activationBody.id);
        return new ResponseEntity<>("Activation Successfull", HttpStatus.ACCEPTED);
    }

    @AllArgsConstructor
    static class ActivationBody {
        String username;
        String email;
        int id;
    }
}
