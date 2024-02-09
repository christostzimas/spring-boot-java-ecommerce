package com.ct_ecommerce.eshop.Order;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        OrderRepository = orderRepository;
    }

    /**
     * Get all orders for specific user
     * @param user The authenticated user.
     * @Errors ** IllegalStateException, Exception
     * @Returns List of all the user orders
     */
    public List<Order> getUserOrders(AppUser user){
        /** Get all orders of user */
        List<Order> orders = OrderRepository.findOrdersByUser(user);

        if (orders.isEmpty()) {
            throw new IllegalStateException();
        }

        return orders;
    }

    /**
     * Get order by order_number
     * @param orderNo The order number.
     * @Errors Exception
     * @Returns The order with specific order number
     */
    public Order getOrderByOrderNumber(Long orderNo){
        return OrderRepository.getReferenceByOrderNumber(orderNo);
    }

    /**
     * Save new order
     * @param order The order object
     * @Errors Exception
     */
    @Transactional
    public Order saveNewOrder(Order order) {
        return OrderRepository.save(order);
    }

    /**
     * Delete order by id
     * @param id The id of the order
     * @Errors IllegalArgumentException, RuntimeException
     */
    public void deleteByID(int id){
        try{
            OrderRepository.deleteById(id);
        } catch(IllegalArgumentException ex){
            throw new IllegalArgumentException("Id is not provided");
        }catch(Exception ex){
            throw new RuntimeException("Error fetching order", ex);
        }
    }
}
