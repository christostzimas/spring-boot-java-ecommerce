package com.ct_ecommerce.eshop.Order;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.OrderQuantities.OrderQuantities;
import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.Product.ProductService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import com.ct_ecommerce.eshop.dto.UnavailableProductsDTO;
import com.mysql.cj.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for all requests related to orders
 * @Variable OrderService ** service layer for orders.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products/fridge") ** route prefix
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService OrderService;
    private final ResponseService ResponseService;
    private final ProductService ProductService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public OrderController(OrderService OrderService, ResponseService ResponseService, ProductService ProductService){
        this.OrderService = OrderService;
        this.ResponseService = ResponseService;
        this.ProductService = ProductService;
    }

    /**
     * Get all orders for specific user
     * @GetMapping ** = get request.
     * @Errors RuntimeException
     * @Returns http response
     */
    @GetMapping("/my-orders")
    public ResponseEntity<Order> getUsersOrders(@AuthenticationPrincipal AppUser user){
        try{
            List<Order> orders = OrderService.getUserOrders(user);

            return ResponseService.SuccessResponse(orders);
        } catch (Exception ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Submit new order
     * @PostMapping ** = post request.
     * @Errors IllegalStateException, RuntimeException
     * @Returns http response
     */
    @PostMapping("/submit-new-order")
    public ResponseEntity submitNewOrder(@AuthenticationPrincipal AppUser user, @RequestBody Order recievedOrder){
        Map<Integer, Integer> productQuantitiesMap = new HashMap<>();

        /** total order price */
        double totalOrderPrice = 0.0;

        try{
            /** create new order object and assign address & user */
            Order order = new Order();
            order.setAddress(recievedOrder.getAddress());
            order.setUser(user);
            recievedOrder.setUser(user);
            /** loop order quantities and set the order */
            for (OrderQuantities orderQuantity : recievedOrder.getQuantities()) {
                orderQuantity.setOrder(order);
                /** save values to HashMap id as key quantity as value */
                productQuantitiesMap.put(orderQuantity.getProduct().getId(), orderQuantity.getQuantity());

                /** Calculate total order price*/
                totalOrderPrice += orderQuantity.getProduct().getPrice() * orderQuantity.getQuantity();
            }
            /** Set order price and order number */
            order.setTotalOrderPrice(totalOrderPrice);
            Long orderNo = System.currentTimeMillis();
            order.setOrderNumber(orderNo);

            /** get all ordered products ids from the keys of hashed map */
            Set<Integer> keys = productQuantitiesMap.keySet();

            /** Get all products by ids */
             List<Product> orderedProducts = ProductService.getAllOrderedProducts(keys);

             /** Check if all ordered products have enough stock */
             List<UnavailableProductsDTO> getProductsAvaliability = ProductService.checkProductsAvaliability(orderedProducts, productQuantitiesMap);

             /** if list is not empty some products don't have enough stock */
             if(!getProductsAvaliability.isEmpty()){
                return ResponseService.BadRequestResponse(getProductsAvaliability.toString());
            }
            order.setQuantities(recievedOrder.getQuantities());
            /** save order */
            OrderService.saveNewOrder(order);

            /** subtract order quantities from products */
            ProductService.updateProductStockFromList(productQuantitiesMap);

            //TODO: create receipt + send to email

            String message = "Order " + orderNo + " was submitted successfully";

            return ResponseService.SuccessResponseWithMsg(message);
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }
}
