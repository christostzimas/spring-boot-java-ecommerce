package com.ct_ecommerce.eshop.Order;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for orders
 * @Variable OrderRepository ** repository layer for orders.
 */

@Service
public class OrderService {
    private final OrderRepository OrderRepository;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param orderRepository ** used for db actions
     */
    public OrderService(OrderRepository orderRepository) {
        OrderRepository = orderRepository;
    }

    /**
     * Get all orders for specific user
     * @Errors ** IllegalStateException, RuntimeException
     */
    public List<Order> getUserOrders(AppUser user){
        try {
            List<Order> orders = OrderRepository.findOrdersByUser(user);

            if (orders.isEmpty()) {
                throw new IllegalStateException("No orders found");
            }
            return orders;
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("No orders found");
        } catch(Exception ex){
            throw new RuntimeException("Error getting orders of user", ex);
        }
    }

    /**
     * Saves new order
     * @Errors RuntimeException
     */
    @Transactional
    public Order saveNewOrder(Order order) {
        try{
            return OrderRepository.save(order);

        } catch(Exception ex){
            throw new RuntimeException("Error storing order", ex);
        }
    }
}
