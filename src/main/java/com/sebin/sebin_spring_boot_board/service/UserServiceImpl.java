package com.sebin.sebin_spring_boot_board.service;

import com.sebin.sebin_spring_boot_board.dto.UserDto;
import com.sebin.sebin_spring_boot_board.entity.User;
import com.sebin.sebin_spring_boot_board.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(UserDto.@Valid RegisterRequest registerRequest) {
            userRepository.save(registerRequest.toEntity());
    }

    @Override
    public Optional<User> login(UserDto.@Valid LoginRequest loginRequest) {

        return userRepository.findByUsername(loginRequest.getUsername())
                .filter(user -> {
                    return user.getPassword().equals(loginRequest.getPassword());
                });
    }
}
