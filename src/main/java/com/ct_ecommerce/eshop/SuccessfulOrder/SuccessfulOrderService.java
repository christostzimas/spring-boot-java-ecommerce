package com.ct_ecommerce.eshop.SuccessfulOrder;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Order.Order;
import com.ct_ecommerce.eshop.Order.OrderService;
import com.ct_ecommerce.eshop.OrderQuantities.OrderQuantities;
import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.SuccessfulOrdersQuantities;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public SuccessfulOrderService(SuccessfulOrderRepository successfulOrderRepository, OrderService orderService) {
        this.successfulOrderRepository = successfulOrderRepository;
        this.orderService = orderService;
    }

    /**
     * Get all successful orders of a specific user
     * @param user ** user object
     * @Errors IllegalArgumentException, Exception
     */
    public List<SuccessfulOrder> getAll(AppUser user) {

        List<SuccessfulOrder> successfulOrders = successfulOrderRepository.findOrdersByUser(user);

        if (successfulOrders.isEmpty()) {
            throw new IllegalStateException();
        }

        return successfulOrders;
    }

    /**
     * Mark order as successful
     * @Transactional **
     * @param orderNo ** order_number of pending order
     */
    @Transactional
    public void markAsSuccessful(Long orderNo){
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
    }

    /**
     * Check if user has bought a product
     * @param productId The id of the product
     * @param user The user of the order
     * @Errors Exception
     * @Returns true if user has bought the product else false
     */
    public boolean hasUserPurchasedProduct(AppUser user, int productId) {
        /** Get all user orders */
        List<SuccessfulOrder> userOrders = successfulOrderRepository.findByUser(user);

        /** loop quantities of orders  and check if user has bought the item*/
        for (SuccessfulOrder order : userOrders) {
            for (SuccessfulOrdersQuantities quantity : order.getQuantities()) {
                if (quantity.getProduct().getId() == productId) {
                    return true;
                }
            }
        }
        return false;
    }
}
