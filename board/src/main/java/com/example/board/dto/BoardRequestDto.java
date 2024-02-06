package com.example.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BoardRequestDto {
    private String title;
    private String contents;
    private String writer;
    private String email;
}
