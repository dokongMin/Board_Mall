package com.dokong.board.repository.board;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Optional<Board> findByBoardTitle(String boardTitle);

    @Query("select b.createdDate from Board b order by b.createdDate desc")
    List<LocalDateTime> findByCreatedDate();

    @Query("select count(b) from Board b where b.user.id = :userId")
    int findByUserId(@Param("userId") Long userId);

    @Query("select b from Board b")
    List<Board> findAll();

    @Query("select b from Board b where b.boardStatus = 'CREATED'")
    List<Board> findAllByBoardStatusCreated();
}
