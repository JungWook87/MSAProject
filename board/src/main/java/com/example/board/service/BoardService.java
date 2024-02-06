package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.model.Board;

import java.util.List;

public interface BoardService {

    List<BoardResponseDto> geBoardList();

    BoardResponseDto createBoard(BoardRequestDto requestDto);

    BoardResponseDto getBoardDetail(Long id);

    BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) throws Exception;
}
