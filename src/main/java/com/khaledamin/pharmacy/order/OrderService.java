package com.khaledamin.pharmacy.order;

import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.cart.CartEntity;
import com.khaledamin.pharmacy.cart.CartRepo;
import com.khaledamin.pharmacy.config.UserConfig;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.cart.CartProductItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.order.CreateOrderRequest;
import com.khaledamin.pharmacy.model.order.CreateOrderResponse;
import com.khaledamin.pharmacy.model.order.Order;
import com.khaledamin.pharmacy.payment.PaymentEntity;
import com.khaledamin.pharmacy.payment.PaymentRepo;
import com.khaledamin.pharmacy.shipping.ShippingEntity;
import com.khaledamin.pharmacy.shipping.ShippingRepo;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRepo;
import com.khaledamin.pharmacy.util.InvoiceNumberGenerator;
import com.twilio.rest.api.v2010.account.Address;
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

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        OrderStatusEntity orderStatus = orderStatusRepo.findByStatusName("Processing");
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        List<AddressEntity> userAddresses = user.getAddresses();
        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();
        List<CartProductItem> cartProductItems = new ArrayList<>();
        double subtotal = 0.0;
        AddressEntity defaultAddress = new AddressEntity();
        List<CartEntity> cartEntities = cartRepo.findByUser(user);
        OrderEntity orderEntity = new OrderEntity();
        orderRepo.save(orderEntity);
        for (AddressEntity address : userAddresses) {
            if (address.getIsDefault() == true) {
                defaultAddress = address;
            }
        }
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
        orderEntity.setIncrementId("00000001");
        orderEntity.setPayment(paymentRepo.findById(request.getPaymentId()).orElseThrow());
        orderEntity.setUser(user);
        orderEntity.setSubtotal(subtotal);
        orderEntity.setShipping(shippingRepo.findById(request.getShippingId()).orElseThrow());
        orderEntity.setUser(user);
        orderEntity.setProducts(orderItemsEntities);
        orderEntity.setTotal(subtotal + (subtotal * shippingRepo.findById(request.getShippingId()).orElseThrow().getShippingAmount()));
        orderRepo.save(orderEntity);

        cartRepo.deleteByUser(user);

        for (OrderItemsEntity orderItemsEntity : orderItemsEntities) {
            CartProductItem cartProductItem = CartProductItem.builder()
                    .productId(orderItemsEntity.getProduct().getProductId())
                    .productUnitPrice(orderItemsEntity.getProduct().getProductUnitPrice())
                    .productImage(orderItemsEntity.getProduct().getProductImage())
                    .productRate(orderItemsEntity.getProduct().getProductRate())
                    .productPackPrice(orderItemsEntity.getProduct().getProductPackPrice())
                    .productDetails(orderItemsEntity.getProduct().getProductDetails())
                    .productBrand(orderItemsEntity.getProduct().getProductBrand())
                    .productWeight(orderItemsEntity.getProduct().getProductWeight())
                    .productUnit(orderItemsEntity.getProduct().getProductUnit())
                    .productName(orderItemsEntity.getProduct().getProductName())
                    .productActivePrincipal(orderItemsEntity.getProduct().getProductActivePrincipal())
                    .quantity(orderItemsEntity.getQuantity())
                    .build();
            cartProductItems.add(cartProductItem);
        }
        return CreateOrderResponse.builder()
                .response(new BaseResponse(true, "Order created successfully"))
                .order(Order.builder()
                        .id(orderEntity.getOrderId())
                        .subtotal(subtotal)
                        .shipping(shippingRepo.findById(request.getShippingId()).orElseThrow().getShippingAmount())
                        .status(orderStatus.getStatusName())
                        .total(subtotal + (subtotal * shippingRepo.findById(request.getShippingId()).orElseThrow().getShippingAmount()))
                        .email(user.getEmail())
                        .lastname(user.getLastname())
                        .firstname(user.getFirstname())
                        .incrementId("00000001")
                        .discount(0.0)
                        .dateCreated(userConfig.getNow())
                        .address(defaultAddress)
                        .payment(paymentRepo.findById(request.getPaymentId()).orElseThrow().getPaymentName())
                        .products(cartProductItems)
                        .build())
                .build();
    }

    public List<Order> getCurrentOrders(long userId) {
        List<OrderEntity> orderEntities = orderRepo.getCurrentOrders(userId);
        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            List<OrderItemsEntity> orderProducts = orderEntity.getProducts();
            List<CartProductItem> cartProductItems = new ArrayList<>();
            Order order = Order.builder()
                    .status(orderEntity.getOrderStatus().getStatusName())
                    .total(orderEntity.getTotal())
                    .email(orderEntity.getUser().getEmail())
                    .dateCreated(orderEntity.getDateCreated())
                    .id(orderEntity.getOrderId())
                    .discount(orderEntity.getDiscount())
                    .incrementId(orderEntity.getIncrementId())
                    .subtotal(orderEntity.getSubtotal())
                    .firstname(orderEntity.getUser().getFirstname())
                    .lastname(orderEntity.getUser().getLastname())
                    .address(orderEntity.getAddress())
                    .payment(orderEntity.getPayment().getPaymentName())
                    .shipping(orderEntity.getShipping().getShippingAmount() * orderEntity.getTotal())
                    .products(new ArrayList<>())
                    .build();
            for (OrderItemsEntity orderItem : orderProducts) {
                if (orderItem.getOrder().getOrderId() == orderEntity.getOrderId()) {
                    CartProductItem cartProductItem = CartProductItem.builder()
                            .productId(orderItem.getProduct().getProductId())
                            .productImage(orderItem.getProduct().getProductImage())
                            .productRate(orderItem.getProduct().getProductRate())
                            .productBrand(orderItem.getProduct().getProductBrand())
                            .productPackPrice(orderItem.getProduct().getProductPackPrice())
                            .productDetails(orderItem.getProduct().getProductDetails())
                            .productWeight(orderItem.getProduct().getProductWeight())
                            .productUnit(orderItem.getProduct().getProductUnit())
                            .productName(orderItem.getProduct().getProductName())
                            .productActivePrincipal(orderItem.getProduct().getProductActivePrincipal())
                            .productUnitPrice(orderItem.getProduct().getProductUnitPrice())
                            .quantity(orderItem.getQuantity())
                            .build();
                    cartProductItems.add(cartProductItem);
                } else {
                    continue;
                }
                order.setProducts(cartProductItems);
            }
            orders.add(order);
        }
        return orders;
    }

    public List<Order> getPreviousOrders(long userId) {
        List<OrderEntity> orderEntities = orderRepo.getPreviousOrders(userId);
        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            List<OrderItemsEntity> orderProducts = orderEntity.getProducts();
            List<CartProductItem> cartProductItems = new ArrayList<>();
            Order order = Order.builder()
                    .status(orderEntity.getOrderStatus().getStatusName())
                    .total(orderEntity.getTotal())
                    .email(orderEntity.getUser().getEmail())
                    .dateCreated(orderEntity.getDateCreated())
                    .id(orderEntity.getOrderId())
                    .discount(orderEntity.getDiscount())
                    .incrementId(orderEntity.getIncrementId())
                    .subtotal(orderEntity.getSubtotal())
                    .firstname(orderEntity.getUser().getFirstname())
                    .lastname(orderEntity.getUser().getLastname())
                    .address(orderEntity.getAddress())
                    .payment(orderEntity.getPayment().getPaymentName())
                    .shipping(orderEntity.getShipping().getShippingAmount() * orderEntity.getSubtotal())
                    .products(new ArrayList<>())
                    .build();
            for (OrderItemsEntity orderItem : orderProducts) {
                if (orderItem.getOrder().getOrderId() == orderEntity.getOrderId()) {
                    CartProductItem cartProductItem = CartProductItem.builder()
                            .productId(orderItem.getProduct().getProductId())
                            .productImage(orderItem.getProduct().getProductImage())
                            .productRate(orderItem.getProduct().getProductRate())
                            .productBrand(orderItem.getProduct().getProductBrand())
                            .productPackPrice(orderItem.getProduct().getProductPackPrice())
                            .productDetails(orderItem.getProduct().getProductDetails())
                            .productWeight(orderItem.getProduct().getProductWeight())
                            .productUnit(orderItem.getProduct().getProductUnit())
                            .productName(orderItem.getProduct().getProductName())
                            .productActivePrincipal(orderItem.getProduct().getProductActivePrincipal())
                            .productUnitPrice(orderItem.getProduct().getProductUnitPrice())
                            .quantity(orderItem.getQuantity())
                            .build();
                    cartProductItems.add(cartProductItem);
                } else {
                    continue;
                }
                order.setProducts(cartProductItems);
            }
            orders.add(order);
        }
        return orders;
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

    public CreateOrderResponse reorder(long userId, long orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId).orElseThrow();
        Order order;
        List<CartProductItem> cartProductItems = new ArrayList<>();
        List<OrderItemsEntity> orderItemsEntities = orderEntity.getProducts();
        if (orderEntity.getOrderStatus().getStatusName().equals("Complete") || orderEntity.getOrderStatus().getStatusName().equals("Canceled") || orderEntity.getOrderStatus().getStatusName().equals("Returned")) {
            OrderEntity newOrder = orderEntity;
            newOrder.setDateCreated(userConfig.getNow());
            UserEntity user = userRepo.findById(userId).orElseThrow();
            List<AddressEntity> addresses = user.getAddresses();
            AddressEntity defaultAddress = new AddressEntity();
            for (AddressEntity address : addresses) {
                if (address.getIsDefault() == true) {
                    defaultAddress = address;
                }
            }
            newOrder.setAddress(defaultAddress);
            newOrder.setOrderStatus(orderStatusRepo.findByStatusName("Processing"));
            orderRepo.save(newOrder);
            for (OrderItemsEntity orderItemsEntity : orderItemsEntities) {
                CartProductItem cartProductItem = CartProductItem.builder()
                        .quantity(orderItemsEntity.getQuantity())
                        .productId(orderItemsEntity.getProduct().getProductId())
                        .productDetails(orderItemsEntity.getProduct().getProductDetails())
                        .productName(orderItemsEntity.getProduct().getProductName())
                        .productBrand(orderItemsEntity.getProduct().getProductBrand())
                        .productImage(orderItemsEntity.getProduct().getProductImage())
                        .productRate(orderItemsEntity.getProduct().getProductRate())
                        .productUnitPrice(orderItemsEntity.getProduct().getProductUnitPrice())
                        .productWeight(orderItemsEntity.getProduct().getProductWeight())
                        .productPackPrice(orderItemsEntity.getProduct().getProductPackPrice())
                        .productActivePrincipal(orderItemsEntity.getProduct().getProductActivePrincipal())
                        .productUnit(orderItemsEntity.getProduct().getProductUnit())
                        .build();
                cartProductItems.add(cartProductItem);
            }
            order = Order.builder()
                    .status("Processing")
                    .products(cartProductItems)
                    .payment(newOrder.getPayment().getPaymentName())
                    .firstname(newOrder.getUser().getFirstname())
                    .email(newOrder.getUser().getEmail())
                    .lastname(newOrder.getUser().getLastname())
                    .total(newOrder.getTotal())
                    .subtotal(newOrder.getSubtotal())
                    .discount(newOrder.getDiscount())
                    .id(newOrder.getOrderId())
                    .address(newOrder.getAddress())
                    .incrementId(newOrder.getIncrementId())
                    .dateCreated(userConfig.getNow())
                    .shipping(newOrder.getShipping().getShippingAmount())
                    .build();
            return CreateOrderResponse.builder()
                    .response(BaseResponse.builder().status(true).message("Order recreated successfully").build())
                    .order(order)
                    .build();
        } else {
            for (OrderItemsEntity orderItemsEntity : orderItemsEntities) {
                CartProductItem cartProductItem = CartProductItem.builder()
                        .quantity(orderItemsEntity.getQuantity())
                        .productId(orderItemsEntity.getProduct().getProductId())
                        .productDetails(orderItemsEntity.getProduct().getProductDetails())
                        .productName(orderItemsEntity.getProduct().getProductName())
                        .productBrand(orderItemsEntity.getProduct().getProductBrand())
                        .productImage(orderItemsEntity.getProduct().getProductImage())
                        .productRate(orderItemsEntity.getProduct().getProductRate())
                        .productUnitPrice(orderItemsEntity.getProduct().getProductUnitPrice())
                        .productWeight(orderItemsEntity.getProduct().getProductWeight())
                        .productPackPrice(orderItemsEntity.getProduct().getProductPackPrice())
                        .productActivePrincipal(orderItemsEntity.getProduct().getProductActivePrincipal())
                        .productUnit(orderItemsEntity.getProduct().getProductUnit())
                        .build();
                cartProductItems.add(cartProductItem);
            }
            order = Order.builder()
                    .status(orderEntity.getOrderStatus().getStatusName())
                    .products(cartProductItems)
                    .payment(orderEntity.getPayment().getPaymentName())
                    .firstname(orderEntity.getUser().getFirstname())
                    .email(orderEntity.getUser().getEmail())
                    .lastname(orderEntity.getUser().getLastname())
                    .total(orderEntity.getTotal())
                    .subtotal(orderEntity.getSubtotal())
                    .discount(orderEntity.getDiscount())
                    .id(orderEntity.getOrderId())
                    .address(orderEntity.getAddress())
                    .incrementId(orderEntity.getIncrementId())
                    .dateCreated(orderEntity.getDateCreated())
                    .shipping(orderEntity.getShipping().getShippingAmount())
                    .build();
            return CreateOrderResponse.builder().order(order).response(BaseResponse.builder().status(false).message("Can't reorder in this stage").build()).build();
        }
    }
}
