package com.sebin.sebin_spring_boot_board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Comment {

    private Long id;
    private String content;
    private Board board; // 어느 게시글의 댓글인지
    private User user; // 누가 작성했는지
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}
