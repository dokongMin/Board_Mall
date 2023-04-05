package com.dokong.board.repository.product;

import com.dokong.board.web.dto.product.ProductSearchCondition;
import com.dokong.board.web.dto.product.SearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    List<SearchProductDto> search(ProductSearchCondition condition);

    Page<SearchProductDto> searchPage(ProductSearchCondition condition, Pageable pageable);
}
