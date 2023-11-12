package com.khaledamin.pharmacy.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private long orderItemId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId",referencedColumnName = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "productId",referencedColumnName = "product_id")
    private ProductEntity product;

    @Column(name = "quantity",columnDefinition = "BIGINT")
    private int quantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderId",referencedColumnName = "order_id")
    private OrderEntity order;

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}

