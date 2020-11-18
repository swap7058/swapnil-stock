package com.prabhu.userservice.service;

import com.prabhu.userservice.dto.UserDto;
import com.prabhu.userservice.entities.User;
import com.prabhu.userservice.exceptions.AccountActivationException;
import com.prabhu.userservice.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * This belongs to user-service
 *
 * @author Prabhu Madipalli
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo repo;

    private final ModelMapper mapper;

    private final Type listType;

    @Autowired
    public UserServiceImpl(UserRepo repo, ModelMapper mapper) {
        this.listType = new TypeToken<List<UserDto>>() {
        }.getType();
        this.mapper = mapper;
        this.repo = repo;
    }

    @Override
    public Optional<UserDto> findById(@NotNull int id) {
        Optional<User> byId = repo.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        UserDto userDto = this.mapper.map(byId.get(), UserDto.class);
        return Optional.of(userDto);
    }

    @Override
    public Optional<UserDto> findByUsername(@Size(max = 25, min = 3) @NotNull @Pattern(regexp = "^[A-Za-z]*$") String username) {
        Optional<User> byId = repo.findUserByUsername(username);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        UserDto userDto = this.mapper.map(byId.get(), UserDto.class);
        return Optional.of(userDto);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return this.mapper.<List<UserDto>>map(repo.findAll(), this.listType);
    }

    @Override
    public Iterable<UserDto> getByName(String name) {
        List<User> foundUsers =
                this.repo.findUsersByFirstNameContainingOrLastNameContaining(name, name);
        return this.mapper.map(foundUsers, listType);
    }

    @Override
    public Optional<UserDto> findByEmailId(@Email String email) {
        Optional<User> userByEmail = repo.findUserByEmail(email);
        if (userByEmail.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(this.mapper.map(userByEmail.get(), UserDto.class));
    }

    @Override
    public void saveCustomer(User user) {
        this.repo.save(user);
    }

    @Override
    public void activate(String username, String email, int userId) throws AccountActivationException {
        Optional<UserDto> user = this.findById(userId);
        if (user.isEmpty()) {
            throw new AccountActivationException("Account with that userId doesnt exist");
        }
        if (user.get().getEmail().equals(email) && user.get().getUsername().equals(username)) {
            repo.setCondirmedTrue(userId);
        } else {
            throw new AccountActivationException("All details didn't match");
        }
    }
}
