package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponseDto> geBoardList() {
        return boardRepository.findAllBylastModifiedDateDesc().stream().map(BoardResponseDto::new).toList();
        // 수정일시 기준 내림차순
        // findAll은 JPA가 기본적으로 제공해 주지만, 수정일시 내림차순은 BoardRepository에서 따로 선언 필요
        // BoardResponseDto에서 Board 엔티티를 넣으면 매개변수 생성자가 실행
        // map(BoardResponseDto::new)를 통해 간편하게 dto로 바꿔줄 수 있다.
    }

    @Override
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Override
    public BoardResponseDto getBoardDetail(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        // id를 가진 데이터를 boardRepository에서 찾아서 BoardResponseDto 객체로 만들어서 반환
        // 만약 boardRepository에 해당 id의 데이터가 없다면, 예외처리한다.
    }

    @Override
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) throws Exception{
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );

        if(!requestDto.getEmail().equals(board.getEmail())){
            throw new Exception("게시글 작성자가 아닙니다.");
        }

        // 여기부터 다시

        return new BoardResponseDto(board);
    }
}
