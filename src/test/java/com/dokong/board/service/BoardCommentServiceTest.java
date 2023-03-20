package com.dokong.board.service;

import com.dokong.board.repository.BoardCommentRepository;
import com.dokong.board.web.dto.BoardCommentDto;
import com.dokong.board.web.service.BoardCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BoardCommentServiceTest {

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @Test
    @DisplayName("댓글_생성")
    public void saveBoardComment () throws Exception{
        // given
        BoardCommentDto boardCommentDto = getBoardComment();
        boardCommentService.saveBoardComment(boardCommentDto);
        // then
        assertThat(boardCommentRepository.findAll().get(0).getCommentContent()).isEqualTo(boardCommentDto.getCommentContent());
     }

     @Test
     @DisplayName("댓글_수정")
     public void updateBoardComment () throws Exception{
         // given
         BoardCommentDto boardCommentDto = getBoardComment();
         boardCommentService.saveBoardComment(boardCommentDto);


         BoardCommentDto updateBoardCommentDto = BoardCommentDto.builder()
                 .commentContent("수정된 댓글입니다.")
                 .build();
         boardCommentService.updateBoardComment(boardCommentRepository.findAll().get(0).getId(), updateBoardCommentDto);
         // then
        assertThat(boardCommentRepository.findAll().get(0).getCommentContent()).isEqualTo(updateBoardCommentDto.getCommentContent());
      }

      @Test
      @DisplayName("댓글_수정_예외")
      public void updateBoardCommentException () throws Exception{
          // given
          BoardCommentDto boardComment = getBoardComment();
          boardCommentService.saveBoardComment(boardComment);
          // when
          BoardCommentDto updateBoardCommentDto = BoardCommentDto.builder()
                  .commentContent("수정된 댓글입니다.")
                  .build();
          //then
          assertThatThrownBy(() -> boardCommentService.updateBoardComment(100L, updateBoardCommentDto))
                  .isExactlyInstanceOf(IllegalArgumentException.class)
                  .hasMessageContaining("해당 댓글은 존재하지 않습니다.");

       }



    private BoardCommentDto getBoardComment() {
        return BoardCommentDto.builder()
                .commentContent("댓글입니다.")
                .build();
    }

}