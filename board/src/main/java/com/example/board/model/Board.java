package com.example.board.model;

import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
// 허용 범위를 설정하여 private는 proxy객체를 만들지 못하고, public은 무분별한 객체 생성이 됨으로 protected로 설정
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 3000)
    private String contents;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String email;

    @Builder
    public Board(String title, String contents, String writer, String email){
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.email = email;
    }

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer = requestDto.getWriter();
        this.email = requestDto.getEmail();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer = requestDto.getWriter();
        this.email = requestDto.getEmail();
    }
}
