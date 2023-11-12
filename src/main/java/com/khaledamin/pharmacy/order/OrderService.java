package com.khaledamin.pharmacy.order;

import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.address.AddressRepo;
import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.cart.CartEntity;
import com.khaledamin.pharmacy.cart.CartRepo;
import com.khaledamin.pharmacy.config.TwilioConfig;
import com.khaledamin.pharmacy.config.UserConfig;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.User;
import com.khaledamin.pharmacy.model.cart.CartProductItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.order.CreateOrderRequest;
import com.khaledamin.pharmacy.model.order.CreateOrderResponse;
import com.khaledamin.pharmacy.model.order.Order;
import com.khaledamin.pharmacy.model.order.ReorderRequest;
import com.khaledamin.pharmacy.notification.NotificationEntity;
import com.khaledamin.pharmacy.notification.NotificationRepo;
import com.khaledamin.pharmacy.payment.PaymentEntity;
import com.khaledamin.pharmacy.payment.PaymentRepo;
import com.khaledamin.pharmacy.shipping.ShippingEntity;
import com.khaledamin.pharmacy.shipping.ShippingRepo;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRepo;
import com.khaledamin.pharmacy.util.InvoiceNumberGenerator;
import com.twilio.rest.api.v2010.account.Address;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ShippingRepo shippingRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private OrderStatusRepo orderStatusRepo;
    @Autowired
    private OrderItemsRepo orderItemsRepo;
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private TwilioConfig twilioConfig;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        OrderStatusEntity orderStatus = orderStatusRepo.findByStatusName("Processing");
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        AddressEntity defaultAddress = addressRepo.findByUserAndDefaultAddress(user.getId());
        double subtotal = 0.0;
        OrderEntity orderEntity = new OrderEntity();
        orderRepo.save(orderEntity);
        List<CartEntity> cartEntities = cartRepo.findByUser(user);
        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();
        for (CartEntity cart : cartEntities) {
            OrderItemsEntity orderItem = OrderItemsEntity.builder()
                    .user(cart.getUser())
                    .order(orderEntity)
                    .quantity(cart.getQuantity())
                    .product(cart.getProduct()).build();
            orderItemsRepo.save(orderItem);
            orderItemsEntities.add(orderItem);
            subtotal += (cart.getProduct().getProductPackPrice() * cart.getQuantity());
        }
        orderEntity.setOrderStatus(orderStatus);
        orderEntity.setAddress(defaultAddress);
        orderEntity.setDateCreated(userConfig.getNow());
        orderEntity.setDiscount(0.0);
        orderEntity.setIncrementId("0" + orderEntity.getOrderId());
        orderEntity.setPayment(paymentRepo.findById(request.getPaymentId()).orElseThrow());
        orderEntity.setUser(user);
        orderEntity.setSubtotal(subtotal);
        orderEntity.setShipping(shippingRepo.findById(request.getShippingId()).orElseThrow());
        orderEntity.setUser(user);
        orderEntity.setProducts(orderItemsEntities);
        orderEntity.setTotal(subtotal + (subtotal * shippingRepo.findById(request.getShippingId()).orElseThrow().getShippingAmount()));
        orderRepo.save(orderEntity);
        cartRepo.deleteByUser(user);
        PhoneNumber to = new PhoneNumber("+2" + user.getPhone());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
        String notificationTitle = "Order #" + orderEntity.getIncrementId() + " is on the way to you";
        Message.creator(to, from, notificationTitle).create();
        NotificationEntity notification = NotificationEntity.builder()
                .notificationTitle(notificationTitle)
                .notificationIcon(null)
                .notificationTime(userConfig.getNow())
                .build();
        notificationRepo.save(notification);
        user.getNotifications().add(notification);
        userRepo.save(user);
        return CreateOrderResponse.builder()
                .response(new BaseResponse(true, "Order created successfully"))
                .order(orderEntity)
                .build();
    }

    public List<OrderEntity> getCurrentOrders(long userId) {
        List<OrderEntity> orderEntities = orderRepo.getCurrentOrders(userId);
        return orderEntities;
    }

    public List<OrderEntity> getPreviousOrders(long userId) {
        List<OrderEntity> orderEntities = orderRepo.getPreviousOrders(userId);
        return orderEntities;
    }

    public BaseResponse cancelOrder(long orderId) {
        OrderEntity order = orderRepo.findById(orderId).orElseThrow();
        switch (order.getOrderStatus().getStatusName()) {
            case "Processing":
                order.setOrderStatus(orderStatusRepo.findByStatusName("Canceled"));
                orderRepo.save(order);
                return BaseResponse.builder().status(true).message("Order is canceled").build();
        }
        return BaseResponse.builder().status(false).message("Order can't be cancelled").build();
    }

    public CreateOrderResponse reorder(ReorderRequest request) {
        OrderEntity orderEntity = orderRepo.findById(request.getOrderId()).orElseThrow();
        List<OrderItemsEntity> orderItemsEntities = orderEntity.getProducts();
        List<OrderItemsEntity> newOrderItems = new ArrayList<>();
        AddressEntity address = addressRepo.findByUserAndDefaultAddress(request.getUserId());
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        OrderEntity newOrder = new OrderEntity();
        newOrderItems.addAll(orderItemsEntities);
        if (orderEntity.getOrderStatus().getStatusName().equals("Complete") || orderEntity.getOrderStatus().getStatusName().equals("Canceled") || orderEntity.getOrderStatus().getStatusName().equals("Returned")) {
            newOrder = OrderEntity.builder()
                    .products(newOrderItems)
                    .orderStatus(orderStatusRepo.findByStatusName("Processing"))
                    .total(orderEntity.getTotal())
                    .discount(orderEntity.getDiscount())
                    .dateCreated(userConfig.getNow())
                    .address(address)
                    .subtotal(orderEntity.getSubtotal())
                    .payment(orderEntity.getPayment())
                    .shipping(orderEntity.getShipping())
                    .user(userRepo.findById(request.getUserId()).orElseThrow())
                    .build();
            orderRepo.save(newOrder);
            newOrder.setIncrementId("0" + newOrder.getOrderId());
            orderRepo.save(newOrder);
        }
        PhoneNumber to = new PhoneNumber("+2" + user.getPhone());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
        String notificationTitle = "Order #" + newOrder.getIncrementId() + " is on the way to you";
        Message.creator(to, from, notificationTitle).create();
        NotificationEntity notification = NotificationEntity.builder()
                .notificationTitle(notificationTitle)
                .notificationIcon(null)
                .notificationTime(userConfig.getNow())
                .build();
        notificationRepo.save(notification);
        user.getNotifications().add(notification);
        userRepo.save(user);
        return CreateOrderResponse.builder().response(BaseResponse.builder().status(true).message("Order recreated successfully").build()).order(newOrder).build();
    }
}


