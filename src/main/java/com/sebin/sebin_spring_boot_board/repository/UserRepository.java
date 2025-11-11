package com.sebin.sebin_spring_boot_board.repository;

import com.sebin.sebin_spring_boot_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
