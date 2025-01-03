package com.ct_ecommerce.eshop.Order;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Find order using user who owns it
     * @param user The user authenticated user who owns the order.
     */
    @Query("SELECT order FROM Order order WHERE order.user=:user")
    List<Order> findOrdersByUser(@Param("user") AppUser user);

    /**
     * Find order using order_number
     * @param orderNo The order number generated during saving.
     */
    @Query("SELECT order FROM Order order WHERE order.orderNumber=:orderNo")
    Order getReferenceByOrderNumber(@Param("orderNo") Long orderNo);
}
