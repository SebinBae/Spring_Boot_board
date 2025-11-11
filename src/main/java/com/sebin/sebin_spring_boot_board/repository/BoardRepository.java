package com.sebin.sebin_spring_boot_board.repository;

import com.sebin.sebin_spring_boot_board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
