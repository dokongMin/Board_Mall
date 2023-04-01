package com.dokong.board.repository;

import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from orders o")
    List<Order> findAll();

    @Query("select o from orders o where o.orderStatus = :orderStatus")
    List<Order> findAllByOrderStatus(@Param("orderStatus") OrderStatus orderStatus);

    @Query("select o from orders o where o.user.id = :userId")
    List<Order> findAllByUserId(@Param("userId") Long userId);
}
