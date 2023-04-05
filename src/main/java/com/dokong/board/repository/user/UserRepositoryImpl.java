package com.dokong.board.repository.user;

import com.dokong.board.web.dto.userdto.QSearchUserDto;
import com.dokong.board.web.dto.userdto.SearchUserDto;
import com.dokong.board.web.dto.userdto.UserSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dokong.board.domain.user.QUser.*;
import static org.springframework.util.StringUtils.hasText;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SearchUserDto> search(UserSearchCondition condition) {
        return queryFactory
                .select(new QSearchUserDto(
                        user.username,
                        user.name,
                        user.phoneNumber,
                        user.email,
                        user.userRole
                )).from(user)
                .where(usernameEq(condition.getUsername()),
                        nameEq(condition.getName())
//                        userroleEq(condition.getUserRole())
                ).fetch();

    }

//    @Override
//    public Page<SearchUserDto> searchPageSimple(UserSearchCondition condition, Pageable pageable) {
//        QueryResults<SearchUserDto> results = queryFactory
//                .select(new QSearchUserDto(
//                        user.username,
//                        user.name,
//                        user.phoneNumber,
//                        user.email,
//                        user.userRole
//                )).from(user)
//                .where(usernameEq(condition.getUsername()),
//                        nameEq(condition.getName())
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<SearchUserDto> content = results.getResults();
//        long total = results.getTotal();
//
//        return new PageImpl<>(content, pageable, total);
//    }

    @Override
    public Page<SearchUserDto> searchPageComplex(UserSearchCondition condition, Pageable pageable) {
        List<SearchUserDto> content = queryFactory
                .select(new QSearchUserDto(
                        user.username,
                        user.name,
                        user.phoneNumber,
                        user.email,
                        user.userRole
                )).from(user)
                .where(usernameEq(condition.getUsername()),
                        nameEq(condition.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(usernameEq(condition.getUsername()),
                        nameEq(condition.getName())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

//    private BooleanExpression userroleEq(UserRole userRole) {
//        return hasText(userRole.toString()) ? user.userRole.eq(userRole) : null;
//    }


    private BooleanExpression nameEq(String name) {
        return hasText(name) ? user.name.eq(name) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? user.username.eq(username) : null;
    }


}
