package com.dokong.board.repository;

import com.dokong.board.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardTitle(String boardTitle);

}
