package com.dokong.board.repository;


import com.dokong.board.domain.board.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
//@Query("select bl from BoardLike bl where bl.user.id = :userId and bl.board.id = :boardId)
    @Query("select bl from BoardLike bl where bl.user.id = :userId and bl.board.id = :boardId")
    Optional<BoardLike> findExistLike(Long userId, Long boardId);

    @Query("select count(bl) from BoardLike bl where bl.board.id = :boardId")
    long countBoardLike(@Param("boardId") Long boardId);
}
