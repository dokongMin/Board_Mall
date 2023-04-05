package com.dokong.board.repository.product;

import com.dokong.board.domain.product.QProduct;
import com.dokong.board.web.dto.product.ProductSearchCondition;
import com.dokong.board.web.dto.product.QSearchProductDto;
import com.dokong.board.web.dto.product.SearchProductDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dokong.board.domain.product.QProduct.product;
import static org.springframework.util.StringUtils.hasText;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SearchProductDto> search(ProductSearchCondition condition) {
        return queryFactory
                .select(new QSearchProductDto(
                        product.itemName,
                        product.itemPrice,
                        product.itemStock
                ))
                .from(product)
                .where(
                        itemnameEq(condition.getItemName()),
                        itempriceGoe(condition.getPriceGoe()),
                        itempriceLoe(condition.getPriceLoe())
                ).fetch();
    }

    @Override
    public Page<SearchProductDto> searchPage(ProductSearchCondition condition, Pageable pageable) {
        List<SearchProductDto> result = queryFactory
                .select(new QSearchProductDto(
                        product.itemName,
                        product.itemPrice,
                        product.itemStock
                ))
                .from(product)
                .where(
                        itemnameEq(condition.getItemName()),
                        itempriceGoe(condition.getPriceGoe()),
                        itempriceLoe(condition.getPriceLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        itemnameEq(condition.getItemName()),
                        itempriceGoe(condition.getPriceGoe()),
                        itempriceLoe(condition.getPriceLoe())
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression itempriceLoe(int priceLoe) {
        return priceLoe == 0 ? null : product.itemPrice.loe(priceLoe);
    }

    private BooleanExpression itempriceGoe(int priceGoe) {
        return priceGoe == 0 ? null : product.itemPrice.goe(priceGoe);
    }

    private BooleanExpression itemnameEq(String itemName) {
        return hasText(itemName) ? product.itemName.eq(itemName) : null;
    }


}
