package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.SuccessResponseDto;

import java.util.List;

public interface BoardService {

    List<BoardResponseDto> getBoardList();

    BoardResponseDto createBoard(BoardRequestDto requestDto);

    BoardResponseDto getBoardDetail(Long id);

    BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) throws Exception;

    SuccessResponseDto deleteBoard(Long id, BoardRequestDto requestDto);
}
