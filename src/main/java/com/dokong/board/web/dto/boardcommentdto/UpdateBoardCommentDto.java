package com.dokong.board.web.dto.boardcommentdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBoardCommentDto {

    @NotBlank
    private String commentContent;
}
