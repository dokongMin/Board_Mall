package com.dokong.board.domain.board;

import com.dokong.board.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("게시글_생성")
    public void createBoard () throws Exception{
        // given
        User user = User.builder()
                .username("alsghks")
                .password("12345")
                .name("정민환")
                .build();

        Board board = Board.builder()
                .boardTitle("첫 게시글")
                .boardContent("안녕하세요.")
                .build();

        // when
        board.writeBoard(user);
        // then
        assertThat(board.getUser().getName()).isEqualTo("정민환");
        assertThat(user.getBoards().get(0)).isEqualTo(board);
     }

     @Test
     @DisplayName("댓글_생성")
     public void createComment () throws Exception{
         // given
         User user = User.builder()
                 .username("alsghks")
                 .password("12345")
                 .name("정민환")
                 .build();

         Board board = Board.builder()
                 .boardTitle("첫 게시글")
                 .boardContent("안녕하세요.")
                 .build();

         BoardComment boardComment = BoardComment.builder()
                 .commentContent("첫 댓글입니다.")
                 .build();
         // when 
         boardComment.writeComment(user, board);
         // then
         assertThat(user.getBoardComments().get(0).getCommentContent()).isEqualTo("첫 댓글입니다.");
         assertThat(board.getBoardComments().get(0).getCommentContent()).isEqualTo("첫 댓글입니다.");
      }
      
      @Test
      @DisplayName("좋아요_생성")
      public void createLike () throws Exception{
          // given
          User user = User.builder()
                  .username("alsghks")
                  .password("12345")
                  .name("정민환")
                  .build();

          Board board = Board.builder()
                  .boardTitle("첫 게시글")
                  .boardContent("안녕하세요.")
                  .build();
          // when 
          BoardLike boardLike = new BoardLike();
          boardLike.pushLike(user, board);
          boardLike.pushLike(user, board);
          // then
          assertThat(board.getLikeCount()).isEqualTo(2);
       }

}