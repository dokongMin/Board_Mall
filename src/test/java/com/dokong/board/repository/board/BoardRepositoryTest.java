package com.dokong.board.repository.board;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardStatus;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dokong.board.domain.board.BoardStatus.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {


    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("전체 게시글 중에 상태가 삭제가 아닌 게시글을 조회한다.")
    @Test
    void findAllByBoardStatusCreated () throws Exception{
        // given
        boardRepository.save(getBoardCreated());
        boardRepository.save(getBoardCreated());
        boardRepository.save(getBoardDeleted());
        // when
        List<Board> result = boardRepository.findAllByBoardStatusCreated();
        // then
        assertThat(result).hasSize(2)
                .extracting("boardStatus")
                .containsExactlyInAnyOrder(
                        CREATED, CREATED
                );
     }

    private Board getBoardCreated() {
        return Board.builder()
                .boardTitle("aaa")
                .boardContent("bbb")
                .boardStatus(CREATED)
                .build();
    }
    private Board getBoardDeleted() {
        return Board.builder()
                .boardTitle("aaa")
                .boardContent("bbb")
                .boardStatus(BoardStatus.DELETED)
                .build();
    }
}