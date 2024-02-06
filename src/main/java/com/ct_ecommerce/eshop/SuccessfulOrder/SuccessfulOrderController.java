package com.ct_ecommerce.eshop.SuccessfulOrder;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.AppUsers.AppUserService;
import com.ct_ecommerce.eshop.Order.OrderService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import com.ct_ecommerce.eshop.dto.OrderNumberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests related to successful orders
 * @Variable OrderService ** service layer for orders.
 * @Variable SuccessfulOrderService ** service layer for successful orders.
 * @Variable AppUserService ** service layer for users.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products/orders") ** route prefix
 */

@RestController
@RequestMapping("/api/orders")
public class SuccessfulOrderController {
    private final OrderService OrderService;
    private final ResponseService ResponseService;
    private final AppUserService AppUserService;
    private final SuccessfulOrderService SuccessfulOrderService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public SuccessfulOrderController(OrderService OrderService, ResponseService ResponseService, SuccessfulOrderService SuccessfulOrderService, AppUserService AppUserService){
        this.OrderService = OrderService;
        this.ResponseService = ResponseService;
        this.SuccessfulOrderService = SuccessfulOrderService;
        this.AppUserService = AppUserService;
    }

    /**
     * Get all successful orders for specific user
     * @GetMapping ** = get request.
     * @Param user ** The authenticated user
     * @Errors RuntimeException
     * @Returns http response
     */
    @GetMapping("/my-successful-orders")
    public ResponseEntity<SuccessfulOrder> getUsersOrders(@AuthenticationPrincipal AppUser user){
        try{
            List<SuccessfulOrder> orders = SuccessfulOrderService.getAll(user);

            return ResponseService.SuccessResponse(orders);
        } catch (Exception ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Mark order as successful
     * @PostMapping ** = post request.
     * @Param user ** The authenticated user
     * @Param orderNumberDTO ** orderNumberDTO object containing the order number
     * @Errors Exception
     * @Returns http response
     */
    @PostMapping("/mark-as-successful")
    public ResponseEntity markOrderAsSuccessful(@AuthenticationPrincipal AppUser user, @RequestBody OrderNumberDTO orderNumberDTO){
        /** Check if user is the administrator */
        if(!AppUserService.isUserAdmin(user)){
            System.out.println(user.toString());
            return ResponseService.BadRequestResponse("Not allowed");
        }

        try {
            SuccessfulOrderService.markAsSuccessful(orderNumberDTO.getOrderNumber());

            return ResponseService.SuccessResponse();

        } catch(Exception ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }

    }
}
