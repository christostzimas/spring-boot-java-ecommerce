package com.ct_ecommerce.eshop.SuccessfulOrder;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuccessfulOrderRepository extends JpaRepository<SuccessfulOrder, Integer> {

    /** Get successful order using the user who owns it */
    @Query("SELECT order FROM SuccessfulOrder order WHERE order.user=:user")
    List<SuccessfulOrder> findOrdersByUser(@Param("user") AppUser user);
}
