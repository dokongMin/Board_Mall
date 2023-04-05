package com.dokong.board.repository.board;

import com.dokong.board.web.dto.boardlikedto.BoardSearchCondition;
import com.dokong.board.web.dto.boardlikedto.QSearchBoardDto;
import com.dokong.board.web.dto.boardlikedto.SearchBoardDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dokong.board.domain.board.QBoard.board;
import static com.dokong.board.domain.user.QUser.user;
import static org.springframework.util.StringUtils.hasText;

public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SearchBoardDto> search(BoardSearchCondition condition) {
        return queryFactory
                .select(new QSearchBoardDto(
                        board.boardTitle,
                        user.username
                ))
                .from(board)
                .leftJoin(board.user, user)
                .where(
                        boardtitleEq(condition.getBoardTitle()),
                        usernameEq(condition.getUsername())
                ).fetch();
    }

    @Override
    public Page<SearchBoardDto> searchPage(BoardSearchCondition condition, Pageable pageable) {
        List<SearchBoardDto> result = queryFactory
                .select(new QSearchBoardDto(
                        board.boardTitle,
                        user.username
                ))
                .from(board)
                .innerJoin(board.user, user)
                .where(
                        boardtitleEq(condition.getBoardTitle()),
                        usernameEq(condition.getUsername())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.user, user)
                .where(
                        boardtitleEq(condition.getBoardTitle()),
                        usernameEq(condition.getUsername())
                );
        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? user.username.eq(username) : null;
    }

    private BooleanExpression boardtitleEq(String boardTitle) {
        return hasText(boardTitle) ? board.boardTitle.eq(boardTitle) : null;
    }
}
