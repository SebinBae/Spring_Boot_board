package com.sebin.sebin_spring_boot_board.service;

import com.sebin.sebin_spring_boot_board.dto.BoardDto;
import com.sebin.sebin_spring_boot_board.entity.Board;
import com.sebin.sebin_spring_boot_board.entity.User;
import com.sebin.sebin_spring_boot_board.repository.BoardRepository;
import com.sebin.sebin_spring_boot_board.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 작성자 본인 체크
        if (!board.getUser().getId().equals(userId)) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }

    @Override
    public void updateBoard(Long id, BoardDto.@Valid Request request, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 작성자 본인 체크
        if (!board.getUser().getId().equals(userId)) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        // 수정
        board.update(request.getTitle(), request.getContent());
    }

    @Override
    public BoardDto.DetailResponse getBoardDetail(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return BoardDto.DetailResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUser().getUsername()) // 작성자 정보
                .createdAt(board.getCreatedAt())
                .build();

    }

    @Override
    public Long createBoard(BoardDto.@Valid Request request, Long userId) {

        User user = userRepository.findById(userId).orElseThrow( () -> new RuntimeException("유저 매칭 실패"));

        Board board = Board.builder().title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Board saved = boardRepository.save(board);
        return saved.getId();
    }

    @Override
    public int getTotalBoardCount() {
        return (int) boardRepository.count();
    }

    @Override
    public List<BoardDto.ListResponse> getBoardList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());

        return boardRepository.findAll(pageable)
                .stream()
                .map(board -> BoardDto.ListResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .username(board.getUser().getUsername())
                        .createdAt(board.getCreatedAt())
                        .build())
                .toList();
    }
}
