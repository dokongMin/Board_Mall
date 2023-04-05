package com.dokong.board.repository.board;

import com.dokong.board.web.dto.boardlikedto.BoardSearchCondition;
import com.dokong.board.web.dto.boardlikedto.SearchBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    List<SearchBoardDto> search(BoardSearchCondition condition);

    Page<SearchBoardDto> searchPage(BoardSearchCondition condition, Pageable pageable);
}
