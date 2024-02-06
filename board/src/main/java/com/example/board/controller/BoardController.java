package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.model.Board;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private final BoardService boardService;
    
    // 전체조회
    @GetMapping
    public List<BoardResponseDto> getBoardList(){
        return boardService.geBoardList();
    }
    
    // 상세조회
    @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoardDetail(id);
    }

    // 신규생성
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }

    // 수정
    @PatchMapping("/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) throws Exception{
        return boardService.updateBoard(id, requestDto);
    }
//
//    // 삭제
//    @DeleteMapping
}
