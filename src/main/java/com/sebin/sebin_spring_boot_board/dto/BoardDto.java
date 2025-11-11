package com.sebin.sebin_spring_boot_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class BoardDto{

    @Data
    public static class Request{

        @NotBlank(message = "제목은 필수입니다")
        @Size(max = 200,message = "제목은 최대 200글자까지 입력 가능합니다")
        private String title;

        @NotBlank(message = "내용은 필수입니다")
        private String content;

    }

    // 게시글 목록 응답 DTO
    @Data
    @Builder
    public static class ListResponse {
        private Long id;
        private String title;
        private String username;
        private int viewCount;
        private LocalDateTime createdAt;

        public ListResponse(Long id, String title, String username, int viewCount, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.username = username;
            this.viewCount = viewCount;
            this.createdAt = createdAt;
        }
    }

    @Data
    @Builder
    public static class DetailResponse{
        private Long id;
        private String title;
        private String content;
        private String username;
        private String userId;
        private int viewCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DetailResponse(Long id, String title, String content, String username, String userId,
                              int viewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.userId = userId;
            this.viewCount = viewCount;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

    }

}
