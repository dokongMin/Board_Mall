package com.dokong.board.repository;

import com.dokong.board.domain.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query("select c from CartProduct c where c.user.id = :id")
    List<CartProduct> findAllByUserId(@Param("id") Long id);

    @Query("select c from CartProduct c where c.user.id = :userId and c.product.id = :productId")
    Optional<CartProduct> checkExistCartProduct(@Param("userId") Long userId, @Param("productId") Long productId);
}
