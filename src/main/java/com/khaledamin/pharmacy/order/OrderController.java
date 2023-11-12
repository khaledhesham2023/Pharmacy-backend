package com.khaledamin.pharmacy.order;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.model.order.CreateOrderRequest;
import com.khaledamin.pharmacy.model.order.CreateOrderResponse;
import com.khaledamin.pharmacy.model.order.Order;
import com.khaledamin.pharmacy.model.order.ReorderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("getCurrentOrders/{userId}")
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<List<OrderEntity>> getCurrentOrders(@PathVariable("userId") long userId){
        return ResponseEntity.ok(orderService.getCurrentOrders(userId));
    }
    @GetMapping("getPreviousOrders/{userId}")
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<List<OrderEntity>> getPreviousOrders(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(orderService.getPreviousOrders(userId));
    }

    @PutMapping("cancelOrder/{orderId}")
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<BaseResponse> cancelOrder(@PathVariable("orderId") long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @PostMapping("reorder")
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<CreateOrderResponse> reorder(@RequestBody ReorderRequest request){
        return ResponseEntity.ok(orderService.reorder(request));
    }
}
