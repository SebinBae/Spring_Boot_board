package com.sebin.sebin_spring_boot_board.service;

import com.sebin.sebin_spring_boot_board.dto.BoardDto;
import jakarta.validation.Valid;

import java.util.List;

public interface BoardService {
    void deleteBoard(Long id, Long userId);

    void updateBoard(Long id, BoardDto.@Valid Request request, Long userId);

    BoardDto.DetailResponse getBoardDetail(Long id);

    Long createBoard(BoardDto.@Valid Request request, Long userId);

    int getTotalBoardCount();

    List<BoardDto.ListResponse> getBoardList(int page, int pageSize);
}
