package com.khaledamin.pharmacy.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "carts",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "product_id")
    private ProductEntity product;

    @Column(name = "quantity",columnDefinition = "BIGINT")
    private int quantity;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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
}

