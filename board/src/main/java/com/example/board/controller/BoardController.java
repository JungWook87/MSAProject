package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.SuccessResponseDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    
    // 전체조회
    @GetMapping
    public List<BoardResponseDto> getBoardList(){
        return boardService.getBoardList();
    }
    
    // 상세조회
    @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoardDetail(id);
    }

    // 신규생성
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
        log.info("{}", "requestDto :: " +  requestDto.getTitle() + " , " + requestDto.getContents()  + " , " + requestDto.getWriter() + " , " +  requestDto.getEmail());
        return boardService.createBoard(requestDto);
    }

    // 수정
    @PatchMapping("/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) throws Exception{
        return boardService.updateBoard(id, requestDto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public SuccessResponseDto deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        return boardService.deleteBoard(id, requestDto);
    }
}
