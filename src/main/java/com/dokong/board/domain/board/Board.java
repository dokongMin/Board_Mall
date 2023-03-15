package com.dokong.board.domain.board;

import com.dokong.board.domain.baseentity.BaseEntity;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private String boardTitle;
    private String boardContent;
    private Long likeCount;

}
