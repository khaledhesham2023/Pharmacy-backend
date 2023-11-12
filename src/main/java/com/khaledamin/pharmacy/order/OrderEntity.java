package com.khaledamin.pharmacy.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.payment.PaymentEntity;
import com.khaledamin.pharmacy.shipping.ShippingEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "orders", schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", columnDefinition = "BIGINT", unique = true)
    private long orderId;

    @Column(name = "date_created", columnDefinition = "VARCHAR(255)")
    private String dateCreated;

    @Column(name = "subtotal_amount", columnDefinition = "double")
    private double subtotal;

    @OneToOne
    @JoinColumn(name = "shippingId", referencedColumnName = "shipping_id")
    private ShippingEntity shipping;

    @Column(name = "total_amount", columnDefinition = "double")
    private double total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "address_id")
    private AddressEntity address;

    @Column(name = "discount", columnDefinition = "double")
    private double discount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    @JsonIgnore
    private UserEntity user;

    @Column(name = "increment_id", columnDefinition = "VARCHAR(255)")
    private String incrementId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "orderItemId", referencedColumnName = "order_item_id"))
    private List<OrderItemsEntity> products;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cart_id"))
//    private List<CartEntity> cartEntities;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId", referencedColumnName = "payment_id")
    private PaymentEntity payment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statusId", referencedColumnName = "status_id")
    private OrderStatusEntity orderStatus;

    public long getOrderId() {
        return orderId;
    }


    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public ShippingEntity getShipping() {
        return shipping;
    }

    public void setShipping(ShippingEntity shipping) {
        this.shipping = shipping;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
    }

    public List<OrderItemsEntity> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItemsEntity> products) {
        this.products = products;
    }


//    public List<CartEntity> getCartEntities() {
//        return cartEntities;
//    }
//
//    public void setCartEntities(List<CartEntity> cartEntities) {
//        this.cartEntities = cartEntities;
//    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public OrderStatusEntity getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEntity orderStatus) {
        this.orderStatus = orderStatus;
    }

}