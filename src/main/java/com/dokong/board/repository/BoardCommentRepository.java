package com.dokong.board.repository;

import com.dokong.board.domain.board.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {


}
