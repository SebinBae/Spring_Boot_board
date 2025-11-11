package com.sebin.sebin_spring_boot_board.service;

import com.sebin.sebin_spring_boot_board.dto.UserDto;
import com.sebin.sebin_spring_boot_board.entity.User;
import jakarta.validation.Valid;

import java.util.Optional;

public interface UserService {

    void register(UserDto.@Valid RegisterRequest registerRequest);

    Optional<User> login(UserDto.@Valid LoginRequest loginRequest);
}
