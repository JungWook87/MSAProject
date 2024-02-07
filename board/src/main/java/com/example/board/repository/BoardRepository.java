package com.example.board.repository;

import com.example.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
                                            // <엔티티, 엔티티의 ID값>

    List<Board> findAllByOrderByModifiedAtDesc();
}
