package com.dokong.board.repository;

import com.dokong.board.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardTitle(String boardTitle);

    @Query("select b.createdDate from Board b order by b.createdDate desc")
    List<LocalDateTime> findByCreatedDate();

    @Query("select count(b) from Board b where b.user.id = :userId")
    int findByUserId(@Param("userId") Long userId);

}
