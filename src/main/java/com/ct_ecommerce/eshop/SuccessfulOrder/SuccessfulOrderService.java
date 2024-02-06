package com.ct_ecommerce.eshop.SuccessfulOrder;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Order.Order;
import com.ct_ecommerce.eshop.Order.OrderService;
import com.ct_ecommerce.eshop.OrderQuantities.OrderQuantities;
import com.ct_ecommerce.eshop.SuccessfulOrdersQuantities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for orders
 * @Variable SuccessfulOrderRepository ** repository layer for orders.
 * @Variable orderService ** service layer for pending orders.
 */

@Service
public class SuccessfulOrderService {
    private final SuccessfulOrderRepository successfulOrderRepository;
    private final OrderService orderService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param successfulOrderRepository ** used for db actions
     * @param orderService used for accessing and retrieving pending order information
     */
    public SuccessfulOrderService(SuccessfulOrderRepository successfulOrderRepository, OrderService orderService) {
        this.successfulOrderRepository = successfulOrderRepository;
        this.orderService = orderService;
    }

    /**
     * Get all successful orders of a specific user
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param user ** user object
     */
    public List<SuccessfulOrder> getAll(AppUser user) {
        try{
            List<SuccessfulOrder> successfulOrders = successfulOrderRepository.findOrdersByUser(user);

            if (successfulOrders.isEmpty()) {
                throw new IllegalStateException("No orders found");
            }

            return successfulOrders;

        }catch (IllegalStateException ex){
            throw new IllegalArgumentException("No orders found");
        } catch(Exception ex){
            throw new RuntimeException("Error getting orders of user", ex);
        }
    }

    /**
     * Get all successful orders of a specific user
     * @Transactional **
     * @param orderNo ** order_number of pending order
     */
    @Transactional
    public void markAsSuccessful(Long orderNo){
        try{
            /** get order from pending orders */
            Order pendingOrder = orderService.getOrderByOrderNumber(orderNo);

            /** lists of pending order and successful */
            List<OrderQuantities> orderQuantitiesList = pendingOrder.getQuantities();
            List<SuccessfulOrdersQuantities> successfulOrderQuantitiesList = new ArrayList<>();

            /** create the new successful order object */
            SuccessfulOrder order = new SuccessfulOrder();

            /** loop order list and cast quantities to successfulOrdersQuantities */
            for (OrderQuantities orderQuantities : orderQuantitiesList) {
                SuccessfulOrdersQuantities quantities = new SuccessfulOrdersQuantities();
                quantities.setProduct(orderQuantities.getProduct());
                quantities.setQuantity(orderQuantities.getQuantity());
                quantities.setOrder(order);

                successfulOrderQuantitiesList.add(quantities);
            }

            /** Set the rest fields of the successful order */
            order.setUser(pendingOrder.getUser());
            order.setAddress(pendingOrder.getAddress());
            order.setTotalOrderPrice(pendingOrder.getTotalOrderPrice());
            order.setOrderNumber(pendingOrder.getOrderNumber());
            order.setQuantities(successfulOrderQuantitiesList);

            /** timestamps */
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            /** store successful order */
            successfulOrderRepository.save(order);
            /** delete pending order */
            orderService.deleteByID(pendingOrder.getId());

            //TODO: return id
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error marking order as successful", ex);
        }
    }
}
